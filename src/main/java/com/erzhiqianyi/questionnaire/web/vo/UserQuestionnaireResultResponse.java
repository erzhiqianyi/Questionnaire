package com.erzhiqianyi.questionnaire.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserQuestionnaireResultResponse {
    private UserQuestionnaireResponse userQuestionnaire;
    private List<JudgeResultResponse> judgeResult;
}
