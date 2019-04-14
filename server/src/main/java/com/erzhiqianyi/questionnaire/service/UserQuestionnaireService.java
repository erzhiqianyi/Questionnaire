package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.JudgeResultResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import com.erzhiqianyi.questionnaire.web.vo.UserQuestionnaireResponse;

import java.util.List;

public interface UserQuestionnaireService {
    ResponseResult<Long> createUserQuestionnaire(UserQuestionnaireRequest request);

    ResponseResult<UserQuestionnaireResponse> getUserQuestionnaireById(Long id);

    ResponseResult<List<JudgeResultResponse>> getUserQuestionnaireJudgeResult(Long id);
}
