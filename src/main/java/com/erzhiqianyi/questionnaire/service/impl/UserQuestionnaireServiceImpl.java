package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.UserAnswer;
import com.erzhiqianyi.questionnaire.dao.model.UserQuestionnaire;
import com.erzhiqianyi.questionnaire.dao.repository.UserAnswerRepository;
import com.erzhiqianyi.questionnaire.dao.repository.UserQuestionnaireRepository;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.UserAnswerRequest;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.QuestionResponse;
import com.erzhiqianyi.questionnaire.web.vo.QuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserQuestionnaireServiceImpl implements UserQuestionnaireService {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private JudgeLogicService judgeLogicService;

    @Autowired
    private UserQuestionnaireRepository userQuestionnaireRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public ResponseResult<Long> createUserQuestionnaire(UserQuestionnaireRequest request) {
        ResponseResult<QuestionnaireResponse> questionnaire = questionnaireService
                .getQuestionnaireByCode(request.getCode());
        if (null == questionnaire || !questionnaire.isSuccess()) {
            return ResponseResult.success("questionnaire not exists.");
        }
        if (null == request.getAnswers() || request.getAnswers().isEmpty()) {
            return ResponseResult.success("please answer the questions");
        }

        Long questionnaireId = questionnaire.getResult().getId();
        checkUserAnswer(request.getAnswers(), questionnaire.getResult().getQuestions());
        var userAnswer = calculateTotalScore(request.getAnswers(), questionnaire.getResult().getQuestions());

        Integer totalScore = userAnswer.stream()
                .filter(answer-> null != answer.getScore())
                .map(answer -> answer.getScore())
                .reduce(0, (x, y) -> x + y);
        var judgeLogic = judgeLogicService.judgeScore(totalScore, questionnaire.getResult().getId());
        var userQuestionnaire = new UserQuestionnaire();

        userQuestionnaire.setJudgeLogicId(judgeLogic.isPresent() ? judgeLogic.get().getId() : null);
        userQuestionnaire.setQuestionnaireId(questionnaireId);
        userQuestionnaire.setTotalScore(totalScore);
        userQuestionnaire.setUserId(request.getUserId());

        userQuestionnaireRepository.save(userQuestionnaire);
        var userQuestionnaireId = userQuestionnaire.getId();
        userAnswer.forEach(answer -> answer.setUserQuestionnaireId(userQuestionnaireId));
        userAnswerRepository.saveAll(userAnswer);

        return ResponseResult.success("create user questionnaire success",userQuestionnaireId);
    }


    private List<UserAnswer> calculateTotalScore(List<UserAnswerRequest> request, List<QuestionResponse> quesions) {
        var standQuestionMap = quesions
                .stream().collect(Collectors.toMap(question -> question.getId(), question -> question));
        var userAnswers = request
                .stream()
                .map(answerRequest -> {
                    var userAnswer = new UserAnswer();
                    userAnswer.setQuestionId(answerRequest.getQuestionId());
                    userAnswer.setAnswerId(answerRequest.getAnswerId());
                    userAnswer.setAttach(answerRequest.getAttach());
                    var question = standQuestionMap.get(answerRequest.getQuestionId());
                    if (null == question){
                        return userAnswer;
                    }
                    var standAnswer = question.getAnswers()
                            .stream()
                            .filter(
                                    answer ->
                                            answer.getId() == answerRequest.getAnswerId().intValue())
                            .findFirst();
                    userAnswer.setScore(standAnswer.get().getScore());
                    return userAnswer;
                })
                .collect(Collectors.toList());
        return userAnswers;

    }

    private void checkUserAnswer(List<UserAnswerRequest> request, List<QuestionResponse> questions) {
        var userAnswerMap = request.stream()
                .collect(Collectors.groupingBy(UserAnswerRequest::getQuestionId));

        var standQuestionMap = questions.stream()
                .collect(Collectors.toMap(question -> question.getId(), question -> question));

        //检查是否缺少问题答案
        standQuestionMap.forEach((key, question) -> {
            if (question.isRequired()) {
                return;
            }
            var userAnswer = userAnswerMap.get(key);
            if (null == userAnswer) {
                throw new IllegalArgumentException("please answer the question: " + question.getContent());
            }

            if (userAnswer.size() >= question.getAnswerCount()) {
                throw new IllegalArgumentException(" the question:" + question.getContent() + "answer count is "
                        + question.getAnswerCount() + "please check your answer");
            }
            var standAnswerMap = question.getAnswers().stream()
                    .collect(Collectors.toMap(answer -> answer.getId(), answer -> answer));

            var notStandAnswer = userAnswer.stream().filter(answer -> {
                var standAnswer = standAnswerMap.get(answer.getAnswerId());
                if (null == standAnswer) {
                    return false;
                }
                if (!StringUtils.isEmpty(answer.getAttach()) && !standAnswer.getAttach()) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());

            if (null != notStandAnswer && !notStandAnswer.isEmpty()) {
                throw new IllegalArgumentException("非标准答案之内的答案:" + notStandAnswer.get(0).getAnswerId());
            }
        });

        //检测答卷是否多出问题
        userAnswerMap.forEach((key, answer) -> {
            var standQuestion = standQuestionMap.get(key);
            if (null == standQuestion) {
                throw new IllegalArgumentException("question " + key + "not exists.");
            }
        });

    }
}
