package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Data;

import java.util.List;

@Data
public class QuestionGroupRequest {
    private String code;
    private String name;
    private String remark;
    private List<QuestionGroupDetailRequest> questions;

}
