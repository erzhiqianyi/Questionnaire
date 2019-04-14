package com.erzhiqianyi.questionnaire.web.vo;

public enum ResponseStatusEnum {
    SUCCESS(200,"success"),
    BAD_REQUEST(400,"bad request"),
    ERROR(500,"error");
    private int code;
    private String msg;

    ResponseStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
