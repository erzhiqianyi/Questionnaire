package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Data;

@Data
public class QuestionGroupDetailRequest {
    private String questionContent;
    private Integer sort;

    public QuestionGroupDetailRequest() {
    }

    public QuestionGroupDetailRequest(String sort) {
        this.sort = Integer.valueOf(sort);
    }
}
