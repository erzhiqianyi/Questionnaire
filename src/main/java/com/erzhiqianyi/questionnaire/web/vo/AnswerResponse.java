package com.erzhiqianyi.questionnaire.web.vo;


import com.erzhiqianyi.questionnaire.dao.model.Answer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerResponse {
    private Long id;
    private String content;
    private Boolean attach;
    private Integer score;
    private Integer sort;


    public AnswerResponse() {
    }

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.attach = answer.getAttach();
        this.score = answer.getScore();
        this.sort = answer.getSort();
    }
}
