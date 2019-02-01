package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/questionnaire")
@Log4j2
public class UserQuestionnaireController {

    private UserQuestionnaireService service;

    public UserQuestionnaireController(UserQuestionnaireService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseResult<Long> createQuestionnaire(@RequestBody @Validated UserQuestionnaireRequest request) {
        return service.createUserQuestionnaire(request);
    }

}
