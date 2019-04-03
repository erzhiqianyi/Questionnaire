package com.erzhiqianyi.questionnaire.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.TreeMap;

@Getter
@Setter
@ToString
public class AnswerRequest {

    @NotBlank(message = " title must not be blank")
    @Size(max = 1000)
    private String content;

    private Integer score;


    private Boolean showScore;

    private Boolean attach;

    @NotNull
    private Integer sort;


    public AnswerRequest() {
    }

    public AnswerRequest(String content, String sort) {
        this.content = content;
        this.sort = Integer.parseInt(sort);
    }

    public AnswerRequest(String csvData) {
        if (StringUtils.isEmpty(csvData)) {
            throw new IllegalArgumentException("csv data can't be null");
        }
        String[] arr = csvData.split(",");
        if (arr.length != 4) {
            throw new IllegalArgumentException("illegal csv data ");
        }

        this.content = arr[0];
        this.score = Integer.parseInt(arr[1]);
        Integer attach = Integer.parseInt(arr[2]);
        attach = null == attach ? 0 :attach;
        this.attach = attach.intValue() == 1;
        this.sort = Integer.parseInt(arr[3]);
        this.showScore = false;


    }
}
