package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.dto.QuestionGroupDto;
import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.QuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;

import java.util.List;

public interface QuestionnaireService {
    ResponseResult<Long> createQuestionnaire(QuestionnaireRequest questionnaire);

    ResponseResult<QuestionnaireResponse> getQuestionnaireByCode(String code);

    ResponseResult<QuestionnaireResponse> getQuestionnaireById(Long questionnaireId);

    List<QuestionGroupDto> getQuestionnaireQuestionGroup(Long questionnaireId);

}
