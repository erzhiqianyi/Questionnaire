package com.erzhiqianyi.questionnaire.service.bo;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@Log4j2
public class LogicSymbolTest {


    @Test
    public void judgeInfo() {
        String[][] judgeInfoRule = {
                {"1.5", "GT,null,null", "0"},
                {"1.5", "GT,1,null", "0"},
                {"1.5", "GT,1,1", "0"},
                {"1.5", "GT,null,2", "0"},
                {"1.5", "GT,null,1.5", "0"},
                {"1.5", "GT,null,1", "1"},
                {"1.5", "GTEQ,null,null", "0"},
                {"1.5", "GTEQ,1,null", "0"},
                {"1.5", "GTEQ,1,1", "0"},
                {"1.5", "GTEQ,null,2", "0"},
                {"1.5", "GTEQ,null,1.5", "1"},
                {"1.5", "GTEQ,null,1", "1"},

                {"1.5", "EQ,null,null", "0"},
                {"1.5", "EQ,1,1.5", "0"},
                {"1.5", "EQ,1,1", "0"},
                {"1.5", "EQ,1.5,1.5", "1"},
                {"1.5", "EQ,1,null", "0"},
                {"1.5", "EQ,1.5,null", "1"},
                {"1.5", "EQ,null,1", "0"},
                {"1.5", "EQ,null,1.5", "1"},

                {"1.5", "LT,null,null", "0"},
                {"1.5", "LT,null,1", "0"},
                {"1.5", "LT,1,1", "0"},
                {"1.5", "LT,2,null", "1"},
                {"1.5", "LT,1.5,null", "0"},
                {"1.5", "LT,1,null", "0"},
                {"1.5", "LTEQ,null,null", "0"},
                {"1.5", "LTEQ,null,1", "0"},
                {"1.5", "LTEQ,1,1", "0"},
                {"1.5", "LTEQ,2,null", "1"},
                {"1.5", "LTEQ,1.5,null", "1"},
                {"1.5", "LTEQ,1,null", "0"},

                {"1.5", "BT,null,null", "0"},
                {"1.5", "BT,1,null", "0"},
                {"1.5", "BT,null,1", "0"},
                {"1.5", "BT,1.5,1.5", "0"},
                {"1.5", "BT,2,1", "0"},
                {"1.5", "BT,1,2", "1"},
                {"1.5", "BT,1,1.5", "0"},
                {"1.5", "BT,1.5,2", "0"},

                {"1.5", "BTC,null,null", "0"},
                {"1.5", "BTC,1,null", "0"},
                {"1.5", "BTC,null,1", "0"},
                {"1.5", "BTC,1.5,1.5", "0"},
                {"1.5", "BTC,2,1", "0"},
                {"1.5", "BTC,1,2", "1"},
                {"1.5", "BTC,1,1.5", "1"},
                {"1.5", "BTC,1.5,2", "1"},
                {"1.5", "BTC,1.6,2", "0"},
                {"1.5", "BTC,1,1.4", "0"},

                {"1.5", "BTLC,null,null", "0"},
                {"1.5", "BTLC,1,null", "0"},
                {"1.5", "BTLC,null,1", "0"},
                {"1.5", "BTLC,1.5,1.5", "0"},
                {"1.5", "BTLC,2,1", "0"},
                {"1.5", "BTLC,1,2", "1"},
                {"1.5", "BTLC,1,1.5", "0"},
                {"1.5", "BTLC,1.5,2", "1"},
                {"1.5", "BTLC,1.6,2", "0"},
                {"1.5", "BTLC,1,1.4", "0"},
                {"1.5", "BTRC,null,null", "0"},
                {"1.5", "BTRC,1,null", "0"},
                {"1.5", "BTRC,null,1", "0"},
                {"1.5", "BTRC,1.5,1.5", "0"},
                {"1.5", "BTRC,2,1", "0"},
                {"1.5", "BTRC,1,2", "1"},
                {"1.5", "BTRC,1,1.5", "1"},
                {"1.5", "BTRC,1.5,2", "0"},
                {"1.5", "BTRC,1.6,2", "0"},
                {"1.5", "BTRC,1,1.4", "0"},
        };
        Long i = 1l;
        for (String[] item : judgeInfoRule) {
            Double score = Double.valueOf(item[0]);
            JudgeLogic judgeLogic = new JudgeLogic();
            judgeLogic.setId(i++);
            String[] judgeInfo = item[1].split(",");
            judgeLogic.setSymbol(LogicSymbol.symbol(judgeInfo[0]));
            String minxStr = judgeInfo[1];
            String maxStr = judgeInfo[2];
            if (!("null".equals(maxStr))) {
                judgeLogic.setMaxScore(Double.valueOf(maxStr));
            }
            if (!("null").equals(minxStr)) {
                judgeLogic.setMinScore(Double.valueOf(minxStr));
            }
            boolean expect = item[2].equalsIgnoreCase("1");
            boolean result = LogicSymbol.judgeInfo(judgeLogic, score);
            log.info(Arrays.toString(item) + " expect[" + expect + "],actual[" + result + "]");
            assertEquals(expect, result);


        }

    }

    @Test
    public void judgeBetter() {
    }

    @Test
    public void judgeScore() {
    }

    @Test
    public void compare() {
        double a = 2;
        double b = 2.5;
        System.out.println(Double.compare(2, 1));

    }
}

