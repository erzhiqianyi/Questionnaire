package com.erzhiqianyi.questionnaire.service.bo;

import com.erzhiqianyi.questionnaire.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        for (String[] item : judgeInfoRule) {
            Double score = Double.valueOf(item[0]);
            String[] judgeInfoArray = item[1].split(",");
            String minxStr = judgeInfoArray[1];
            String maxStr = judgeInfoArray[2];
            var judgeInfo = new JudgeInfo() {
                @Override
                public Double getMinScore() {
                    if (!("null").equals(minxStr)) {
                        return Double.valueOf(minxStr);
                    }
                    return null;
                }

                @Override
                public Double getMaxScore() {
                    if (!("null".equals(maxStr))) {
                        return Double.valueOf(maxStr);
                    }
                    return null;
                }

                @Override
                public LogicSymbol getSymbol() {
                    return LogicSymbol.symbol(judgeInfoArray[0]);
                }
            };

            boolean expect = item[2].equalsIgnoreCase("1");
            boolean result = LogicSymbol.judgeInfo(judgeInfo, score);
            log.info(Arrays.toString(item) + " expect[" + expect + "],actual[" + result + "]");
            assertEquals(expect, result);


        }

    }

    @Test
    public void judgeBetter() {
        String[][] judgeInfoArray = {
                {""}
        };

        for (String[] item : judgeInfoArray) {

        }
    }

    @Test
    public void judgeBetterBySame() {
        String[][] judgeInfoArray = {
                {"LT,1,null", "LT,2,null", "0"},
                {"LT,2,null", "LT,1,null", "1"},
                {"LTEQ,1,null", "LTEQ,2,null", "0"},
                {"LTEQ,2,null", "LTEQ,1,null", "1"},
                {"GT,null,1", "GT,null,2", "1"},
                {"GT,null,2", "GT,null,1", "0"},
                {"GTEQ,null,1", "GTEQ,null,2", "1"},
                {"GTEQ,null,2", "GTEQ,null,1", "0"},
                {"BT,1,5", "BT,1,2", "1"},
                {"BT,1,2", "BT,1,5", "0"},
                {"BT,2,3", "BT,1,4", "0"},
                {"BT,2,3", "BT,1,3", "0"},
                {"BT,1,4", "BT,2,3", "1"},
                {"BTC,1,5", "BTC,1,2", "1"},
                {"BTC,1,2", "BTC,1,5", "0"},
                {"BTC,2,3", "BTC,1,4", "0"},
                {"BTC,2,3", "BTC,1,3", "0"},
                {"BTC,1,4", "BTC,2,3", "1"},

        };

        for (String[] item : judgeInfoArray) {
            String[] infoCsv = item[0].split(",");
            String[] anotherCsv = item[1].split(",");
            Integer betterIndex = Integer.valueOf(item[2]);
            String[] betterCsv = item[betterIndex].split(",");
            var one = new JudgeInfo() {
                @Override
                public Double getMinScore() {
                    if (!"null".equals(infoCsv[1])) {
                        return Double.valueOf(infoCsv[1]);
                    }
                    return null;
                }

                @Override
                public Double getMaxScore() {
                    if (!"null".equals(infoCsv[2])) {
                        return Double.valueOf(infoCsv[2]);
                    }
                    return null;
                }

                @Override
                public LogicSymbol getSymbol() {
                    return LogicSymbol.symbol(infoCsv[0]);
                }
            };
            var another = new JudgeInfo() {
                @Override
                public Double getMinScore() {
                    if (!"null".equals(anotherCsv[1])) {
                        return Double.valueOf(anotherCsv[1]);
                    }
                    return null;
                }

                @Override
                public Double getMaxScore() {
                    if (!"null".equals(anotherCsv[2])) {
                        return Double.valueOf(anotherCsv[2]);
                    }
                    return null;
                }

                @Override
                public LogicSymbol getSymbol() {
                    return LogicSymbol.symbol(anotherCsv[0]);
                }
            };
            var result = LogicSymbol.judgeBetterBySame(one, another);
            var better = new JudgeInfo() {
                @Override
                public Double getMinScore() {
                    if (!"null".equals(betterCsv[1])) {
                        return Double.valueOf(betterCsv[1]);
                    }
                    return null;
                }

                @Override
                public Double getMaxScore() {
                    if (!"null".equals(betterCsv[2])) {
                        return Double.valueOf(betterCsv[2]);
                    }
                    return null;
                }

                @Override
                public LogicSymbol getSymbol() {
                    return LogicSymbol.symbol(betterCsv[0]);
                }
            };
            log.info(Arrays.toString(item)
                    + " expect:" + JsonUtil.toJson(better)
                    + " actual:" + JsonUtil.toJson(result));

            assertNotNull(result);
            if (null != better.getMinScore()) {
                assertEquals(better.getMinScore(), result.getMinScore());
            }
            if (null != better.getMaxScore()) {
                assertEquals(better.getMaxScore(), result.getMaxScore());
            }
        }

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

