package com.erzhiqianyi.questionnaire.web.vo;

import com.erzhiqianyi.questionnaire.dao.model.Answer;
import com.erzhiqianyi.questionnaire.dao.model.Question;
import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import com.erzhiqianyi.questionnaire.dao.model.Required;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class QuestionResponse {
    private Long id;

    private String content;

    private QuestionType type;

    private Integer answerCount;

    private Integer sort;

    private Required required;

    private List<AnswerResponse> answers;

    public QuestionResponse() {
    }

    public QuestionResponse(Question item, List<Answer> answers) {
        this.id = item.getId();
        this.content = item.getContent();
        this.type = item.getType();
        this.answerCount = item.getAnswerCount();
        this.sort = item.getSort();
        this.required = item.getRequired();
        answers = null == answers ? new ArrayList<>() : answers;
        this.answers = answers.stream()
                .map(AnswerResponse::new)
                .sorted(Comparator.comparing(AnswerResponse::getSort))
                .collect(Collectors.toList());
    }
}
