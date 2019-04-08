package com.erzhiqianyi.questionnaire.dto;

import com.erzhiqianyi.questionnaire.dao.model.AnswerGroup;
import com.erzhiqianyi.questionnaire.dao.model.AnswerGroupLogic;
import com.erzhiqianyi.questionnaire.dao.model.CalculationType;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AnswerGroupDto {
    private Long id;

    private Long questionnaireId;

    private String code;

    private String name;

    private String remark;

    private CalculationType collectMethod;

    private List<AnswerGroupLogicDto> groupLogic;

    public AnswerGroupDto() {
    }

    public AnswerGroupDto(AnswerGroup group, List<AnswerGroupLogic> answerGroupLogic) {
        this.id = group.getId();
        this.questionnaireId = group.getQuestionnaireId();
        this.code = group.getCode();
        this.name = group.getName();
        this.remark = group.getRemark();
        this.collectMethod = group.getCollectMethod();
        if (null != answerGroupLogic) {
            this.groupLogic = answerGroupLogic.stream()
                    .map(AnswerGroupLogicDto::new)
                    .collect(Collectors.toList());
        }
    }
}
