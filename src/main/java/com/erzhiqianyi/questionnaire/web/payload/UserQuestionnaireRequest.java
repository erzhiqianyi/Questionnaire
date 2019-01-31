package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserQuestionnaireRequest {

    private String questionnaireCode;

    private Integer userId;



}
