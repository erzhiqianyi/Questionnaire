package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.QuestionnaireApplication;
import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@SpringBootTest(classes = QuestionnaireApplication.class)
@RunWith(SpringRunner.class)
public class JudgeLogicServiceImplTest {

    @Autowired
    private JudgeLogicService service;

    private String[] score;
    private Map<Integer, List<JudgeLogic>> judgeMap;

    @Before
    public void init() {
        score = new String[]{
                "30,不及格",
                "60,良好",
                "70,良好",
                "90,良好",
                "91,优秀",
                "101,没有结果"
        };
        String[][] judgeArray = {
                {
                        "60,null,LT,不及格",
                        "60,90,BTC,良好",
                        "null,90,GT,优秀",
                        "null,100,GT,没有结果",
                },
                {
                        "60,null,LT,不及格",
                        "90,null,LTEQ,良好",
                        "100,null,LTEQ,优秀"
                },
                {
                        "null,0,GT,不及格",
                        "null,60,GTEQ,良好",
                        "null,90,GT,优秀",
                        "null,100,GTEQ,没有结果"
                },

        };
        String nullStr = "null";
        judgeMap = Stream.of(judgeArray)
                .collect(Collectors.toMap(array -> array.hashCode(),
                        array -> {
                            var judges = Stream.of(array)
                                    .map(judge -> {
                                        String[] subArray = judge.split(",");
                                        if (null == subArray || subArray.length != 4) {
                                            return null;
                                        }
                                        String min = subArray[0];
                                        var judgeLogic = new JudgeLogic();
                                        if (!nullStr.equals(min)) {
//                                            judgeLogic.setMinScore(Integer.parseInt(min));
                                        }
                                        String max = subArray[1];
                                        if (!nullStr.equals(max)) {
//                                            judgeLogic.setMaxScore(Integer.parseInt(max));
                                        }
                                        LogicSymbol symbol = LogicSymbol.symbol(subArray[2]);
                                        judgeLogic.setSymbol(symbol);
                                        judgeLogic.setMessage(subArray[3]);
                                        return judgeLogic;
                                    })
                                    .filter(item -> null != item)
                                    .collect(Collectors.toList());
                            return judges;

                        }));
    }
}


