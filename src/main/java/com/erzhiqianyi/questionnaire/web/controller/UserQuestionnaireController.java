package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.service.UserQuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import com.erzhiqianyi.questionnaire.web.vo.JudgeResultResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import com.erzhiqianyi.questionnaire.web.vo.UserQuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.UserQuestionnaireResultResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseResult<UserQuestionnaireResponse> getUserQuestionnaire(@PathVariable Long id) {
        return service.getUserQuestionnaireById(id);
    }

    @GetMapping("/result/{id}")
    public ResponseResult<List<JudgeResultResponse>> getUserQuestionnaireJudgeResult(@PathVariable Long id) {
        return service.getUserQuestionnaireJudgeResult(id);
    }

    @GetMapping("all/{id}")
    public ResponseResult<UserQuestionnaireResultResponse> getUserQuestionnaireJudge(@PathVariable Long id) {
        var responseResult = service.getUserQuestionnaireById(id);
        if (null == responseResult || !responseResult.isSuccess()){
            return ResponseResult.badRequest("暂无结果。");
        }

        var judgeResult = service.getUserQuestionnaireJudgeResult(id);
        var result = new UserQuestionnaireResultResponse();
        result.setUserQuestionnaire(responseResult.getResult());
        if (null != judgeResult && judgeResult.isSuccess()){
           result.setJudgeResult(judgeResult.getResult());
        }
        return ResponseResult.success("获取成功",result);
    }



}
