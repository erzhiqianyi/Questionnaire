package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;

public interface UserQuestionnaireService {
    ResponseResult<Long> createUserQuestionnaire(UserQuestionnaireRequest request);
}
