package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.dao.model.LogicSymbol;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@SpringBootTest(classes = QuestionnaireApplication.class)
@RunWith(SpringRunner.class)
public class JudgeLogicServiceImplTest {

    @Autowired
    private JudgeLogicService service;

    private String[] score;
    private List<JudgeLogic> judgeLogics;

    @Before
    public void init() {
        score = new String[]{
                "30,不及格","60,良好", "70,良好", "90,良好",
                "91,优秀","101,优秀"};
        String[] judgeLogicArray = {
                "60,null,LT,不及格",
                "60,90,BTC,良好",
                "null,90,GT,优秀"};
        String nullStr = "null";
        judgeLogics = Stream.of(judgeLogicArray)
                .map(judge -> {
                    String[] array = judge.split(",");
                    if (null == array || array.length != 4) {
                        return null;
                    }
                    String min = array[0];
                    var judgeLogic = new JudgeLogic();
                    if (!nullStr.equals(min)) {
                        judgeLogic.setMinScore(Integer.parseInt(min));
                    }
                    String max = array[1];
                    if (!nullStr.equals(max)) {
                        judgeLogic.setMaxScore(Integer.parseInt(max));
                    }
                    LogicSymbol symbol = LogicSymbol.symbol(array[2]);
                    judgeLogic.setSymbol(symbol);
                    judgeLogic.setMessage(array[3]);
                    return judgeLogic;
                })
                .filter(item -> null != item)
                .collect(Collectors.toList());
    }

    @Test
    public void judgeScore() {
        Stream.of(score).forEach(item -> {
            String[] arr = item.split(",");
            var judgeLogic = service.judgeScore(Integer.parseInt(arr[0]),judgeLogics);
            assertTrue(judgeLogic.isPresent());
            assertEquals(arr[1],judgeLogic.get().getMessage());
        });

    }
}
