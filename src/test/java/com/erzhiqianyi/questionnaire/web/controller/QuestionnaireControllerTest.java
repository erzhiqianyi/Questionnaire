package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.util.JsonUtil;
import com.erzhiqianyi.questionnaire.web.payload.QuestionnaireRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = QuestionnaireApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class QuestionnaireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private QuestionnaireRequest questionnaireRequest;


    @Before
    public void init() {
        DataTest dataTest = new DataTest();
        this.questionnaireRequest = dataTest.getQuestionnaireRequest();
    }

    @Test
    public void createQuestionnaire() throws Exception {
        String data = JsonUtil.toJson(questionnaireRequest);

        mockMvc.perform(
                post("/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"));
    }

    @Test
    public void getQuestionnaire() throws Exception {
        var code = "IntestinalFlora";
        mockMvc.perform(
                get("/questionnaire/code/" + code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("result.code").value(code));

    }
}
