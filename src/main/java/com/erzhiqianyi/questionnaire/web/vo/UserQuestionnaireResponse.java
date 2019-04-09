package com.erzhiqianyi.questionnaire.web.vo;


import com.erzhiqianyi.questionnaire.dao.model.UserAnswer;
import com.erzhiqianyi.questionnaire.dao.model.UserQuestionnaire;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserQuestionnaireResponse {

    private Long id;

    private Long questionnaireId;

    private String questionnaireTitle;

    private String questionnaireCode;

    private String questionnaireRemark;

    private String userId;


    private List<UserAnswerResponse> answers;


    public UserQuestionnaireResponse(QuestionnaireResponse questionnaire, UserQuestionnaire userQuestionnaire, List<UserAnswer> answers) {
        this.id = userQuestionnaire.getId();
        this.questionnaireId = questionnaire.getId();
        this.questionnaireCode = questionnaire.getCode();
        this.questionnaireTitle = questionnaire.getTitle();
        this.questionnaireRemark = questionnaire.getRemark();
        this.userId = userQuestionnaire.getUserId();
        initAnswer(questionnaire.getQuestions(), answers);
    }

    private void initAnswer(List<QuestionResponse> questions, List<UserAnswer> answers) {
        this.answers = questions.stream().map(question -> {
            var answer = new UserAnswerResponse(question,answers);
            return answer;
        }).collect(Collectors.toList());
    }


}
