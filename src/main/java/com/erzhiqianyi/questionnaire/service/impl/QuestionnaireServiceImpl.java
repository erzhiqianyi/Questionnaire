package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.Questionnaire;
import com.erzhiqianyi.questionnaire.dao.repository.QuestionnaireRepository;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.JudgeLogicRequest;
import com.erzhiqianyi.questionnaire.web.payload.QuestionRequest;
import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

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
        if (null != request.getQuestions()) {
            request.getQuestions().stream()
                    .forEach(questionRequest ->
                            saveQuestion(questionRequest, questionnaireId)
                    );
        }

        if (null != request.getJudgeLogic()) {
            request.getJudgeLogic().stream()
                    .forEach(judgeLogicRequest ->
                            saveJudgeLogic(judgeLogicRequest, questionnaireId));

        }
        return ResponseResult.success("create questionnaire  success", questionnaireId);
    }

    private void saveJudgeLogic(JudgeLogicRequest judgeLogicRequest, Long questionnaireId) {
    }

    private void saveQuestion(QuestionRequest questionRequest, Long questionnaireId) {

    }
}
