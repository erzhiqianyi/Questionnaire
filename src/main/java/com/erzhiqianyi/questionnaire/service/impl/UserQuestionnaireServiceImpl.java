package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionnaireServiceImpl implements UserQuestionnaireService {

    @Override
    public ResponseResult<Long> createUserQuestionnaire(UserQuestionnaireRequest request) {
        return ResponseResult.success("create user questionnaire success");
    }
}
