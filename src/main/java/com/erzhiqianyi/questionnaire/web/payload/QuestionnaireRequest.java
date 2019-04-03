package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionnaireRequest {

    @NotBlank(message = " title must not be blank")
    @Size(max = 100)
    private String title;

    @NotBlank(message = " code must not be blank")
    @Size(max = 100)
    private String code;

    @NotNull
    @Size(max = 50)
    private String remark;

    @NotNull
    private List<QuestionRequest> questions;

    private List<JudgeLogicRequest> judgeLogic;

    private List<QuestionGroupRequest> judgeGroup;


}
