package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.Answer;
import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.dao.model.Question;
import com.erzhiqianyi.questionnaire.dao.model.Questionnaire;
import com.erzhiqianyi.questionnaire.dao.repository.AnswerRepository;
import com.erzhiqianyi.questionnaire.dao.repository.JudgeLogicRepository;
import com.erzhiqianyi.questionnaire.dao.repository.QuestionRepository;
import com.erzhiqianyi.questionnaire.dao.repository.QuestionnaireRepository;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.JudgeLogicRequest;
import com.erzhiqianyi.questionnaire.web.payload.QuestionRequest;
import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.QuestionResponse;
import com.erzhiqianyi.questionnaire.web.vo.QuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private JudgeLogicRepository judgeLogicRepository;

    @Override
    public ResponseResult<Long> createQuestionnaire(QuestionnaireRequest request) {
        Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findByCodeOrTitle(request.getCode(), request.getTitle());
        if (optionalQuestionnaire.isPresent()) {
            return ResponseResult.badRequest(" code or title exists");
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCode(request.getCode());
        questionnaire.setTitle(request.getTitle());
        questionnaireRepository.save(questionnaire);
        Long questionnaireId = questionnaire.getId();
        if (null == questionnaireId) {
            return ResponseResult.error(" create questionnaire fail ");
        }

        saveQuestion(request.getQuestions(), questionnaireId);

        saveJudgeLogic(request.getJudgeLogic(), questionnaireId);

        return ResponseResult.success("create questionnaire  success", questionnaireId);
    }

    @Override
    public ResponseResult<QuestionnaireResponse> getQuestionnaireByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return ResponseResult.badRequest("questionnaire not exists");
        }

        Optional<Questionnaire> optional = questionnaireRepository.findByCodeOrTitle(code, null);
        if (!optional.isPresent()) {
            return ResponseResult.badRequest("questionnaire not exists");
        }

        var question = new Question();
        question.setQuestionnaireId(optional.get().getId());
        var questions = questionRepository.findAll(Example.of(question));
        var questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        var answers = answerRepository.findByQuestionIdIn(questionIds);
        var questionnaireResponse = new QuestionnaireResponse(optional.get(),questions,answers);
        return ResponseResult.success("get success", questionnaireResponse);
    }

    private void saveQuestion(List<QuestionRequest> questionRequest, Long questionnaireId) {
        if (null == questionRequest || null == questionnaireId) {
            return;
        }
        questionRequest.forEach(request -> {
            var question = new Question();
            BeanUtils.copyProperties(request, question);
            question.setQuestionnaireId(questionnaireId);
            questionRepository.save(question);
            Long questionId = question.getId();
            var answers = request.getAnswer().stream()
                    .map(answerRequest -> {
                        var answer = new Answer();
                        BeanUtils.copyProperties(answerRequest, answer);
                        answer.setQuestionId(questionId);
                        return answer;
                    }).collect(Collectors.toList());
            answerRepository.saveAll(answers);
        });
    }

    private void saveJudgeLogic(List<JudgeLogicRequest> judgeLogicRequest, Long questionnaireId) {
        if (null == judgeLogicRequest || null == questionnaireId) {
            return;
        }
        var judgeLogics = judgeLogicRequest.stream()
                .map(request -> {
                    var judgeLogic = new JudgeLogic();
                    BeanUtils.copyProperties(request, judgeLogic);
                    judgeLogic.setQuestionnaireId(questionnaireId);
                    return judgeLogic;
                }).collect(Collectors.toList());
        judgeLogicRepository.saveAll(judgeLogics);

    }

}
