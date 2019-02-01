package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.util.JsonUtil;
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

    private UserQuestionnaireRequest userQuestionnaireRequest;

    @Before
    public void init() {
        DataTest dataTest = new DataTest();
        this.userQuestionnaireRequest = dataTest.getUserQuestionnaireRequest();
    }

    @Test
    public void createUserQuestionnaire() throws Exception {
        String data = JsonUtil.toJson(userQuestionnaireRequest);
        mockMvc.perform(
                post("/user/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"));

    }
}