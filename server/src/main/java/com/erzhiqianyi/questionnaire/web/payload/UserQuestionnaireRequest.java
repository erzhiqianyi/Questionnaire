package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class UserQuestionnaireRequest {

    @NotNull
    private String code;

    @NotNull
    private String userId;

    @NotNull
    private List<UserAnswerRequest>  answers;

    public UserQuestionnaireRequest() {
    }
}
