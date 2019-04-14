package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserAnswerRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private Long answerId;

    private String attach;

    public UserAnswerRequest() {
    }

    public UserAnswerRequest(String csvData) {
        if (StringUtils.isEmpty(csvData)) {
            throw new IllegalArgumentException(" csv data can't be null.");
        }

        String[] arr = csvData.split(",");
        if (!(arr.length == 2 || arr.length == 3)) {
            throw new IllegalArgumentException("illegal csv data ");
        }

        this.questionId = Long.parseLong(arr[0]);
        this.answerId = Long.parseLong(arr[1]);
        if (arr.length == 3){
            this.attach = arr[2];
        }


    }
}
