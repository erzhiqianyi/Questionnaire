package com.erzhiqianyi.questionnaire.dto;

import com.erzhiqianyi.questionnaire.dao.model.AnswerGroupLogic;
import com.erzhiqianyi.questionnaire.service.bo.JudgeInfo;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import lombok.Data;

@Data
public class AnswerGroupLogicDto implements JudgeInfo {
    private Long id;

    private Long questionnaireId;

    private String answerGroupCode;

    private Double minScore;

    private Double maxScore;

    private String remark;

    private LogicSymbol symbol;

    public AnswerGroupLogicDto() {
    }

    public AnswerGroupLogicDto(AnswerGroupLogic answerGroupLogic) {
        this.id = answerGroupLogic.getId();
        this.questionnaireId = answerGroupLogic.getQuestionnaireId();
        this.answerGroupCode = answerGroupLogic.getAnswerGroupCode();
        this.minScore = answerGroupLogic.getMinScore();
        this.maxScore = answerGroupLogic.getMaxScore();
        this.symbol = answerGroupLogic.getSymbol();
        this.remark = answerGroupLogic.getRemark();
    }


}
