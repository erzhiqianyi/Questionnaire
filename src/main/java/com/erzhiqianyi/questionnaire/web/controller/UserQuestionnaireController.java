package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import com.erzhiqianyi.questionnaire.web.vo.UserQuestionnaireResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseResult<UserQuestionnaireResponse> getUserQuestionnaireResult(@PathVariable Long id){
        return service.getUserQuestionnaireById(id);
    }

}
