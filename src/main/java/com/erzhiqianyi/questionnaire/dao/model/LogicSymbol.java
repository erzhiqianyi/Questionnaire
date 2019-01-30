package com.erzhiqianyi.questionnaire.dao.model;

public enum  LogicSymbol {
    GTREATE("GT","大于"),
    CREATE_OR_EQUALS("GTE","大于等于"),
    EQUALS("EQ","等于"),
    LESS("LT","小于"),
    LESS_OR_EQUALS("LTQ","小于等于");
    private String symbol;
    private String remark;

    LogicSymbol(String symbol, String remark) {
        this.symbol = symbol;
        this.remark = remark;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getRemark() {
        return remark;
    }
}
