package com.erzhiqianyi.questionnaire.service.bo;

import com.erzhiqianyi.questionnaire.dao.model.JudgeResult;

import java.util.List;

public interface CalScorePercent {
    void calScorePercent(List<JudgeResult> judgeResults);
}
