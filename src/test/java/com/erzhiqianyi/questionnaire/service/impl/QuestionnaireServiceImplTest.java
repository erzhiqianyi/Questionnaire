package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.web.controller.DataTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@SpringBootTest(classes = QuestionnaireApplication.class)
@RunWith(SpringRunner.class)
public class QuestionnaireServiceImplTest {
    @Autowired
    private QuestionnaireService questionnaireService;



    @Test
    public void createQuestionnaire() {
        questionnaireService.createQuestionnaire( new DataTest().getQuestionnaireRequest());
    }

    @Test
    public void getQuestionnaireByCode() {
    }

    @Test
    public void getQuestionnaireById() {
    }
}