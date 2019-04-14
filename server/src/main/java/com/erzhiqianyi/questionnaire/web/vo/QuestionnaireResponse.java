package com.erzhiqianyi.questionnaire.web.vo;

import com.erzhiqianyi.questionnaire.dao.model.Answer;
import com.erzhiqianyi.questionnaire.dao.model.Question;
import com.erzhiqianyi.questionnaire.dao.model.Questionnaire;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class QuestionnaireResponse {

    private Long id;

    private String title;

    private String code;

    private String remark;

    private List<QuestionResponse> questions;

    public QuestionnaireResponse(Questionnaire questionnaire, List<Question> questions, List<Answer> answers) {
        if (null == questionnaire) {
            return;
        }
        this.id = questionnaire.getId();
        this.code = questionnaire.getCode();
        this.title = questionnaire.getTitle();
        this.remark = questionnaire.getRemark();
        this.questions = null == questions ? new ArrayList<>() : initQuestion(questions, answers);
    }

    private List<QuestionResponse> initQuestion(List<Question> questions, List<Answer> answers) {
        answers = null == answers ? new ArrayList<>() : answers;
        Map<Long, List<Answer>> answerMap = answers
                .stream().collect(Collectors.groupingBy(Answer::getQuestionId));
        var questionResponse = questions
                .stream()
                .map(item -> {
                    var response = new QuestionResponse(item, answerMap.get(item.getId()));
                    return response;
                })
                .sorted(Comparator.comparing(QuestionResponse::getSort))
                .collect(Collectors.toList());
        return questionResponse;
    }
}
