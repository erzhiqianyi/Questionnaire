package com.erzhiqianyi.questionnaire.web.vo;

import com.erzhiqianyi.questionnaire.dao.model.UserAnswer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserAnswerResponse {

    private Long id;

    private QuestionResponse question;

    private List<AnswerResponse> answer;

    public UserAnswerResponse() {
    }

    public UserAnswerResponse(QuestionResponse question) {
        this.question = question;
    }

    public UserAnswerResponse(QuestionResponse question, List<UserAnswer> answers) {
        this.question = question;
        if (null == answers){
            return;
        }
        var userAnswerMap = answers.stream().collect(Collectors.groupingBy(UserAnswer::getQuestionId));
        var userAnswers = userAnswerMap.get(question.getId());
        var standAnswerMap = question.getAnswers().stream()
                .collect(Collectors.toMap(standAnswer -> standAnswer.getId(), standAnswer -> standAnswer));
        var userAnswerResponse = userAnswers.stream()
                .map(userAnswer -> {
                    var answerResponse = standAnswerMap.get(userAnswer.getAnswerId());
                    answerResponse.setAttachResult(userAnswer.getAttach());
                    return answerResponse;
                })
                .collect(Collectors.toList());
        this.answer = userAnswerResponse;
    }
}
