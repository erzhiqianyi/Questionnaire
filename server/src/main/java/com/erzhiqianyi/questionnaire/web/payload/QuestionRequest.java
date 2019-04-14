package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import com.erzhiqianyi.questionnaire.dao.model.Required;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

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

    public QuestionRequest() {
    }

    public QuestionRequest(String csvData) {
        if (StringUtils.isEmpty(csvData)) {
            throw new IllegalArgumentException("csv data can't be null");
        }
        String[] arr = csvData.split(",");
        if (arr.length != 5) {
            throw new IllegalArgumentException("illegal csv data ");
        }
        this.content = arr[0];
        this.type = QuestionType.codeType(arr[1].replace(" " ,""));
        if (null == type){
            throw new IllegalArgumentException("question type can't be null.");
        }
        this.answerCount = Integer.parseInt(arr[2].replace(" ",""));
        this.required = Required.valueOf(arr[3].replace(" ",""));
        this.sort = Integer.parseInt(arr[4].replace(" ",""));



    }
}
