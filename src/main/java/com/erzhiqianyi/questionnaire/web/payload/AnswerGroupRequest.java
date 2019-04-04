package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.CalculationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AnswerGroupRequest {
    private String code;
    private String name;
    private String remark;
    private List<AnswerGroupLogicRequest> groupLogic;
    private CalculationType collectMethod;

    public AnswerGroupRequest() {
    }

    public AnswerGroupRequest(String[] csv, List<AnswerGroupLogicRequest> groupLogic) {
        if (null == csv || csv.length != 4) {
            throw new IllegalArgumentException(" illegal csv data ");
        }
        this.code = csv[0].replace(" ","").toUpperCase();
        this.name = csv[1].replace(" ","");
        this.collectMethod = CalculationType.operate(csv[2].replace(" ",""));
        this.groupLogic = groupLogic;
        this.remark = csv[3];
    }
}
