package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.*;
import com.erzhiqianyi.questionnaire.dao.repository.JudgeResultRepository;
import com.erzhiqianyi.questionnaire.dao.repository.UserAnswerRepository;
import com.erzhiqianyi.questionnaire.dao.repository.UserQuestionnaireRepository;
import com.erzhiqianyi.questionnaire.dto.AnswerGroupDto;
import com.erzhiqianyi.questionnaire.dto.QuestionGroupDto;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import com.erzhiqianyi.questionnaire.web.payload.UserAnswerRequest;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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

    @Autowired
    private JudgeResultRepository judgeResultRepository;

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

        var userAnswer = createUserAnswer(request.getAnswers(), questionnaire.getResult().getQuestions());
        var judgeList = judgeAnswer(userAnswer, questionnaireId);

        var userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setQuestionnaireId(questionnaireId);
        userQuestionnaire.setUserId(request.getUserId());

        userQuestionnaireRepository.save(userQuestionnaire);
        var userQuestionnaireId = userQuestionnaire.getId();
        userAnswer.forEach(answer -> answer.setUserQuestionnaireId(userQuestionnaireId));
        judgeList.forEach(result -> result.setUserQuestionnaireId(userQuestionnaireId));

        userAnswerRepository.saveAll(userAnswer);
        judgeResultRepository.saveAll(judgeList);


        return ResponseResult.success("create user questionnaire success", userQuestionnaireId);

    }

    private List<JudgeResult> judgeAnswer(List<UserAnswer> userAnswer, Long questionnaireId) {
        var questionGroupJudgeResult = judgeGroupResult(userAnswer, questionnaireId);
        var answerGroupJudgeResult = judgeAnswerGroupResult(userAnswer, questionnaireId);
        questionGroupJudgeResult.addAll(answerGroupJudgeResult);
        if (CollectionUtils.isEmpty(questionGroupJudgeResult)) {
            return new ArrayList<>();
        }

        List<JudgeLogic> judgeLogicList = judgeLogicService.getQuestionnaireJudgeLogic(questionnaireId);
        if (CollectionUtils.isEmpty(judgeLogicList)) {
            return new ArrayList<>();
        }

        Map<String, List<JudgeLogic>> judgeLogicMap = judgeLogicList
                .stream()
                .collect(Collectors.groupingBy(JudgeLogic::getGroupCode));

        questionGroupJudgeResult.forEach(result -> {
            List<JudgeLogic> judgeLogics = judgeLogicMap.get(result.getGroupCode());
            if (CollectionUtils.isEmpty(judgeLogics)) {
                return;
            }
            Double score = null;
            switch (result.getCalculationType()) {
                case SUM:
                    score = Double.valueOf(result.getTotalScore());
                    break;
                case AVG:
                    score = result.getAverageScore();
                    break;
                case COUNT:
                    score = Double.valueOf(result.getTotalCount());
                    break;
                default:
                    score = result.getAverageScore();
                    break;
            }
            Optional<JudgeLogic> optional = LogicSymbol.judgeScore(score, judgeLogics);
            optional.ifPresent(logic -> {
                result.setJudgeLogicId(logic.getId());
                result.setLevel(logic.getJudgeLevel());
                result.setSuggestion(logic.getMessage());
            });

        });

        return questionGroupJudgeResult;
    }

    private List<JudgeResult> judgeAnswerGroupResult(List<UserAnswer> userAnswer, Long questionnaireId) {
        var answerGroup = questionnaireService.getQuestionnaireAnswerGroup(questionnaireId);
        var judgeResult = answerGroup.stream()
                .map(group -> groupAnswer(userAnswer, group))
                .collect(Collectors.toList());
        return judgeResult;
    }

    private JudgeResult groupAnswer(List<UserAnswer> userAnswer, AnswerGroupDto group) {
        var result = new JudgeResult();
        result.setGroupCode(group.getCode());
        result.setGroupName(group.getName());
        var groupsAnswers = userAnswer.stream()
                .filter(answer ->
                {
                    if (null == group.getGroupLogic()) {
                        return true;
                    } else {
                        return group.getGroupLogic()
                                .stream()
                                .filter(logic -> LogicSymbol.judgeInfo(logic, Double.valueOf(answer.getScore())))
                                .findAny()
                                .isPresent();
                    }
                })
                .collect(Collectors.toList());

        IntSummaryStatistics statistics = groupsAnswers
                .stream().collect(summarizingInt(UserAnswer::getScore));
        result.setTotalCount((int) statistics.getCount());
        result.setTotalScore((int) statistics.getSum());
        result.setAverageScore(statistics.getAverage());
        result.setCalculationType(group.getCollectMethod());

        return result;
    }

    private List<JudgeResult> judgeGroupResult(List<UserAnswer> userAnswer, Long questionnaireId) {

        var questionGroups = questionnaireService.getQuestionnaireQuestionGroup(questionnaireId);

        if (CollectionUtils.isEmpty(questionGroups)) {
            return new ArrayList<>();
        }


        Map<String, QuestionGroupDto> questionGroupMap = questionGroups
                .stream()
                .collect(Collectors.toMap(group -> group.getCode(),
                        group -> group));

        Map<Long, String> questionGroupByCode = mapDetailByGroupCode(questionGroups);

        Map<String, List<JudgeResult>> resultGroup =
                userAnswer
                        .stream()
                        .map(answer -> {
                            var judge = new JudgeResult();
                            judge.setTotalScore(answer.getScore());
                            judge.setGroupCode(questionGroupByCode.get(answer.getQuestionId()));
                            return judge;
                        })
                        .collect(groupingBy(JudgeResult::getGroupCode));
        List<JudgeResult> results =
                resultGroup.entrySet().stream()
                        .map(entry -> {
                            var judge = new JudgeResult();
                            IntSummaryStatistics statistics = entry.getValue()
                                    .stream().collect(summarizingInt(JudgeResult::getTotalScore));
                            judge.setGroupCode(entry.getKey());
                            judge.setTotalScore((int) statistics.getSum());
                            judge.setAverageScore(statistics.getAverage());
                            judge.setTotalCount((int) statistics.getCount());
                            QuestionGroupDto group = questionGroupMap.get(entry.getKey());
                            judge.setGroupName(group.getName());
                            judge.setCalculationType(group.getCalculationType());
                            return judge;
                        })
                        .collect(Collectors.toList());
        return results;

    }


    private Map<Long, String> mapDetailByGroupCode(List<QuestionGroupDto> questionGroups) {
        return questionGroups
                .stream()
                .flatMap(group -> group.getQuestions()
                        .stream()
                        .map(id -> {
                            var singleGroup = new QuestionGroupDto();
                            singleGroup.setCode(group.getCode());
                            singleGroup.setQuestions(
                                    Stream.of(id)
                                            .collect(Collectors.toList()));
                            return singleGroup;
                        }))
                .collect(Collectors.toMap(
                        group -> group.getQuestions().stream().findFirst().get(),
                        group -> group.getCode()));

    }

    @Override
    public ResponseResult<UserQuestionnaireResponse> getUserQuestionnaireById(Long id) {
        var optional = userQuestionnaireRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseResult.badRequest(" result not exists");
        }
        UserQuestionnaire userQuestionnaire = optional.get();
        var questionnaire = questionnaireService.getQuestionnaireById(userQuestionnaire.getQuestionnaireId());
        if (!questionnaire.isSuccess()) {
            return ResponseResult.badRequest(" result not exists");
        }

        List<UserAnswer> answers = userAnswerRepository.findByUserQuestionnaireId(id);

        var userQuestionnaireResponse = new UserQuestionnaireResponse(questionnaire.getResult(), userQuestionnaire, answers, null);
        return ResponseResult.success("获取成功", userQuestionnaireResponse);
    }


    private List<UserAnswer> createUserAnswer(List<UserAnswerRequest> request, List<QuestionResponse> questions) {
        var standQuestionMap = questions
                .stream().collect(Collectors.toMap(question -> question.getId(), question -> question));
        var userAnswers = request
                .stream()
                .map(answerRequest -> {
                    var userAnswer = new UserAnswer();
                    userAnswer.setQuestionId(answerRequest.getQuestionId());
                    userAnswer.setAnswerId(answerRequest.getAnswerId());
                    userAnswer.setAttach(answerRequest.getAttach());
                    var question = standQuestionMap.get(answerRequest.getQuestionId());
                    if (null == question) {
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
                .collect(groupingBy(UserAnswerRequest::getQuestionId));

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
