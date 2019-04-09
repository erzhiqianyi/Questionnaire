package com.erzhiqianyi.questionnaire.web.vo;

import com.erzhiqianyi.questionnaire.dao.model.JudgeResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JudgeResultResponse {
    private Long id;

    private String groupCode;

    private String groupName;

    private Integer totalScore;

    private Integer totalCount;

    private Double averageScore;

    private Double percent;

    private String level;

    private String suggestion;

    private Long userQuestionnaireId;


    public JudgeResultResponse(JudgeResult judgeResult, Long userQuestionnaireId) {
        this.id = judgeResult.getId();
        this.groupCode = judgeResult.getGroupCode();
        this.groupName = judgeResult.getGroupName();
        this.totalScore = judgeResult.getTotalScore();
        this.totalCount = judgeResult.getTotalCount();
        this.averageScore = judgeResult.getAverageScore();
        this.percent = judgeResult.getPercent();
        this.level = null != judgeResult.getLevel() ?  judgeResult.getLevel().getLevel() : null;
        this.suggestion = judgeResult.getSuggestion();
        this.userQuestionnaireId = userQuestionnaireId;

    }
}
