package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.QuestionResponse;
import com.erzhiqianyi.questionnaire.web.vo.QuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaire")
@Log4j2
public class QuestionnaireController {

    private QuestionnaireService service;

    public QuestionnaireController(QuestionnaireService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseResult<Long> createQuestionnaire(@RequestBody @Validated QuestionnaireRequest request) {
        return service.createQuestionnaire(request);
    }

    @GetMapping("/code/{code}")
    public ResponseResult<QuestionnaireResponse> createQuestionnaire(@PathVariable String code) {
        return service.getQuestionResponseByCode(code);
    }
}
