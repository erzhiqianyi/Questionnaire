package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.CalculationType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionGroupRequest {
    private String code;
    private String name;
    private String remark;
    private CalculationType calculationType;
    private List<QuestionGroupDetailRequest> questions;

    public QuestionGroupRequest(String name, String code, String remark) {
        this.name = name;
        this.code = code.toUpperCase();
        this.remark = remark;
        this.calculationType = CalculationType.AVG;
    }
}
