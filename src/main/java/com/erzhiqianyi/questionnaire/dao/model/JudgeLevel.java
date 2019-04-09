package com.erzhiqianyi.questionnaire.dao.model;

public enum JudgeLevel {
    NORMAL("NORMAL","正常"),
    LOW("LOW","轻度"),
    MIDDLE("MIDDLE","中度"),
    DANGER("DANGER","重度");

    private String level;
    private String remark;

    JudgeLevel(String level, String remark) {
        this.level = level;
        this.remark = remark;
    }

    public String getLevel() {
        return level;
    }
}
