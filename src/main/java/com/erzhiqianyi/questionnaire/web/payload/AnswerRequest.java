package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class AnswerRequest {

    @NotBlank(message = " title must not be blank")
    @Size(max = 1000)
    private String content;

    private Integer score;

    private Boolean attach;

    @NotNull
    private Integer sort;


    public AnswerRequest() {
    }

    public AnswerRequest(String content, String sort) {
        this.content = content;
        this.sort = Integer.parseInt(sort);
    }
}
