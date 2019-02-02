package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import com.erzhiqianyi.questionnaire.web.vo.UserQuestionnaireResponse;

public interface UserQuestionnaireService {
    ResponseResult<Long> createUserQuestionnaire(UserQuestionnaireRequest request);

    ResponseResult<UserQuestionnaireResponse> getUserQuestionnaireById(Long id);
}
