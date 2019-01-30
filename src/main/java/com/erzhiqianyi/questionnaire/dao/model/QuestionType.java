package com.erzhiqianyi.questionnaire.dao.model;

public enum  QuestionType {
    SINGLE("S","单选"),
    MULTIPLE("M","多选"),
    FILL_IN("FI","填空")
    ;
    private String code;
    private String  remark;

    QuestionType(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
