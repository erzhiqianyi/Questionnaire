package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.dao.model.QuestionType;
import com.erzhiqianyi.questionnaire.dao.model.Required;
import com.erzhiqianyi.questionnaire.util.JsonUtil;
import com.erzhiqianyi.questionnaire.web.payload.AnswerRequest;
import com.erzhiqianyi.questionnaire.web.payload.QuestionRequest;
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

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    QuestionnaireRequest request;

    @Before
    public void init() {
        request = new QuestionnaireRequest();
        request.setTitle("测试问卷"+System.currentTimeMillis());
        request.setCode("Test"+System.currentTimeMillis());
        var question = new QuestionRequest();
        question.setContent("您的性别是?");
        question.setRequired(Required.Y);
        question.setAnswerCount(1);
        question.setType(QuestionType.SINGLE);
        question.setSort(1);

        String[][] answerArray = {{"男", "1"}, {"女", "2"}, {"保密", "3"}};
        var answer = Arrays.stream(answerArray)
                .map(item -> new AnswerRequest(item[0], item[1]))
                .collect(Collectors.toList());
        question.setAnswer(answer);
        request.setQuestions(Stream.of(question).collect(Collectors.toList()));

    }

    @Test
    public void createQuestionnaire() throws Exception {

        String json = JsonUtil.toJson(request);

        mockMvc.perform(
                post("/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"));
        mockMvc.perform(
                get("/questionnaire/code/"+request.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"));

    }
}
