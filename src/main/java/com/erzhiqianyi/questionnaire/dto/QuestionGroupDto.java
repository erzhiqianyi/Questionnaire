package com.erzhiqianyi.questionnaire.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionGroupDto {
    private Long id;

    private Long questionnaireId;

    private String code;

    private String name;

    private String remark;

    private List<Long> questions;
}
