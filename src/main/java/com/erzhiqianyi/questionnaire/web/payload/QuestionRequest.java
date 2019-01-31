package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
public class QuestionRequest {
    @NotBlank(message = " title must not be blank")
    @Size(max = 100)
    private String title;

    @NotNull
    private QuestionType type;

    private Integer answerCount;

    private Integer sort;

    @NotNull
    private List<AnswerRequest> answer;

    private Boolean required;

}
