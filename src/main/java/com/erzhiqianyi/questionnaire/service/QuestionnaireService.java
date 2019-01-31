package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;

public interface QuestionnaireService {
   ResponseResult<Long> createQuestionnaire(QuestionnaireRequest questionnaire);
}