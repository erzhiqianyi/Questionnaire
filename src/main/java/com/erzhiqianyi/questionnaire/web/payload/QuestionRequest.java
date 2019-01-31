package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import com.erzhiqianyi.questionnaire.dao.model.Required;
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
    @NotBlank(message = " content must not be blank")
    @Size(max = 100)
    private String content;

    @NotNull
    private QuestionType type;

    private Integer answerCount;

    private Integer sort;

    @NotNull
    private List<AnswerRequest> answer;

    private Required required;

}
