package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.dao.model.Questionnaire;
import com.erzhiqianyi.questionnaire.dao.repository.QuestionnaireRepository;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.util.JsonUtil;
import com.erzhiqianyi.questionnaire.web.payload.UserAnswerRequest;
import com.erzhiqianyi.questionnaire.web.payload.UserQuestionnaireRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = QuestionnaireApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserQuestionnaireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    private UserQuestionnaireRequest userQuestionnaireRequest;

    private Long questionnaireId;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Before
    public void init() {
        questionnaireId = questionnaireRepository.findAll().stream().map(Questionnaire::getId).findAny().get();
        var questionResponse = questionnaireService.getQuestionnaireById(questionnaireId);
        assertNotNull(questionResponse);
        assertTrue(questionResponse.isSuccess());
        var questionnaire = questionResponse.getResult();
        userQuestionnaireRequest = new UserQuestionnaireRequest();
        userQuestionnaireRequest.setCode(questionnaire.getCode());
        userQuestionnaireRequest.setUserId("1234");
        var userAnswers = questionnaire.getQuestions()
                .stream()
                .map(question -> {
                    var userAnswer = new UserAnswerRequest();
                    userAnswer.setQuestionId(question.getId());
                    userAnswer.setAnswerId(question.getAnswers().stream().findAny().get().getId());
                    return userAnswer;
                })
                .collect(Collectors.toList());
        userQuestionnaireRequest.setAnswers(userAnswers);
    }

    @Test
    public void createUserQuestionnaire() throws Exception {
        String data = JsonUtil.toJson(userQuestionnaireRequest);
        System.out.println(data);
        mockMvc.perform(
                post("/user/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"));

    }


}
