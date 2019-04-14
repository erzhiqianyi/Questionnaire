package com.erzhiqianyi.questionnaire.web.controller;

import com.erzhiqianyi.questionnaire.web.payload.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataTest {
    private QuestionnaireRequest questionnaireRequest;

    private UserQuestionnaireRequest userQuestionnaireRequest;

    @Test
    public void initQuestion() {
        questionnaireRequest = new QuestionnaireRequest();
        questionnaireRequest.setCode("IntestinalFlora");
        questionnaireRequest.setTitle("肠道菌群检测调查问卷");
        String[][] questionArray = {
                {
                        "健康状况自我评价是?,S,1,Y,1",
                        "非常良好,0,0,1",
                        "非常良好,0,0,2",
                        "普通,0,0,3",
                        "不好,0,0,4",
                        "不好,0,0,5",
                },
                {

                        "是否抽烟?,S,1,Y,2",
                        "是,0,0,1",
                        "否,0,0,2",
                },
                {

                        "是否抽烟?,S,1,Y,3",
                        "是,0,0,1",
                        "否,0,0,2",
                },
                {
                        "运动频率?,S,1,Y,4",
                        "每周3次以上(含3次),0,0,1",
                        "每周2次,0,0,2",
                        "每周1次,0,0,3",
                        "数周1次(不规律),0,0,4",
                },
                {

                        "排便频率?,S,1,Y,5",
                        "每天2次以上(含2次),0,0,1",
                        "每天1次 ,0,0,2",
                        "两天1次,0,0,3",
                        "每周2、3次,0,0,4",
                        "每周1次,0,0,5",
                },
                {

                        "容易排便情况?,S,1,Y,6",
                        "腹泻,0,0,1",
                        "大便稀薄,0,0,2",
                        "大便较湿,0,0,3",
                        "大便适中,0,0,4",
                        "大便较干,0,0,5",
                        "便秘,0,0,6",
                },
                {

                        "是否有代謝综合症?（可多选）,M,0,Y,7",
                        "体重偏高,0,0,1",
                        "血糖偏高,0,0,2",
                        "血压偏高,0,0,3",
                        "三酸甘油脂(TG)偏高,0,0,4",
                        "三酸甘油脂(TG)偏高,0,0,5",
                },
                {

                        "是否有其他疾病病史?（可多选）,M,0,Y,8",
                        "过敏,0,0,1",
                        "糖尿病,0,0,2",
                        "高血压,0,0,3",
                        "动脉硬化,0,0,4",
                        "免疫疾病,0,0,5",
                        "癌症,0,0,6",
                        "过敏,0,0,7",
                        "其他 (疾病名称：           ),0,1,8",
                },
                {

                        "请问您有下列哪些饮食偏好?（可多选）,M,0,Y,9",
                        "肉类多蔬果少,0,0,1",
                        "蔬果多肉类少,0,0,2",
                        "喜爱油炸食品或肥肉,0,0,3",
                        "喜爱吃饭后甜点,0,0,5",
                        "吃正餐常要搭配含糖饮料,0,0,6",
                },
                {

                        "每日水果、蔬菜份量？ (一份约150 克),S,1,Y,10",
                        "1份及以下,0,0,1",
                        "2份,0,0,2",
                        "3份,0,0,3",
                        "4份,0,0,4",
                        "5份,0,0,5",
                        "6份及以上,0,0,6",
                },
                {

                        "每日主食份量?（1份约150克),S,1,Y,11",
                        "1份及以下,0,0,1",
                        "2份,0,0,2",
                        "3份,0,0,3",
                        "4份,0,0,4",
                        "5份,0,0,5",
                        "6份及以上,0,0,6",

                },

                {

                        "每日蛋白质份量?（1份约150克),S,1,Y,12",
                        "1份及以下,0,0,1",
                        "2份,0,0,2",
                        "3份,0,0,3",
                        "4份,0,0,4",
                        "5份,0,0,5",
                        "6份及以上,0,0,6",

                },

                {

                        "每日脂肪、油类份量?（1份约150克),S,1,Y,13",
                        "1份及以下,0,0,1",
                        "2份,0,0,2",
                        "3份,0,0,3",
                        "3份及以上,0,0,4",
                }
        };
        var questions = Stream.of(questionArray).map(item -> {
            var question = new QuestionRequest(item[0]);
            int length = item.length;
            List<String> list = Arrays.asList(item);
            List<String> answerList = list.subList(1, length);
            var answer = answerList.stream()
                    .map(AnswerRequest::new).collect(Collectors.toList());
            question.setAnswer(answer);
            return question;
        }).collect(Collectors.toList());
        questionnaireRequest.setQuestions(questions);
    }


    public QuestionnaireRequest getQuestionnaireRequest() {
        if (null == questionnaireRequest) {
            initQuestion();
        }
        return questionnaireRequest;
    }

    public UserQuestionnaireRequest getUserQuestionnaireRequest() {
        if (null == userQuestionnaireRequest) {
            initUserQuestionnaire();
        }
        return userQuestionnaireRequest;
    }

    private void initUserQuestionnaire() {
        userQuestionnaireRequest = new UserQuestionnaireRequest();
        userQuestionnaireRequest.setCode("IntestinalFlora");
        userQuestionnaireRequest.setUserId("13143514204");
        String[] answerArray = {
                "2,3",
                "8,9",
                "11,12",
                "14,15",
                "19,20",
                "25,26",
                "32,33",
                "32,35",
                "38,39",
                "38,40",
                "47,48",
                "47,49",
                "53,54",
                "60,62",
                "67,69",
                "74,75",
        };
        var answers = Stream.of(answerArray).map(UserAnswerRequest::new)
                .collect(Collectors.toList());
        userQuestionnaireRequest.setAnswers(answers);

    }

}
