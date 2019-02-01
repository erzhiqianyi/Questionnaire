package com.erzhiqianyi.questionnaire.dao.model;

public enum LogicSymbol {
    GREATER("GT", "大于"),
    GREATER_OR_EQUALS("GTE", "大于等于"),
    EQUALS("EQ", "等于"),
    LESS("LT", "小于"),
    LESS_OR_EQUALS("LTQ", "小于等于"),
    BETWEEN("BT", "之间");
    private String symbol;
    private String remark;

    LogicSymbol(String symbol, String remark) {
        this.symbol = symbol;
        this.remark = remark;
    }

    public static boolean judgeLogic(JudgeLogic judgeLogic, Integer score) {
        if (null == judgeLogic) {
            return false;
        }

        if (null == score) {
            return false;
        }
        LogicSymbol logic = judgeLogic.getSymbol();

        Integer minScore = judgeLogic.getMinScore();
        Integer maxScore = judgeLogic.getMaxScore();
        switch (logic) {
            case LESS:
                return null != minScore ? score < minScore : false;
            case LESS_OR_EQUALS:
                return null != minScore ? score <= minScore : false;
            case EQUALS:
                var minEqual = Boolean.FALSE;
                var maxEqual = Boolean.FALSE;
                if (null != minScore) {
                    minEqual = score == minScore;
                }
                if (null != maxScore ){
                    maxEqual = score == maxScore;
                }
                return minEqual || maxEqual ;
            case GREATER_OR_EQUALS:
                return null != maxScore ? score >= maxScore:false;
            case GREATER:
                return null != maxScore ? score > maxScore: false;
            case BETWEEN:
                if (null == minScore || null == maxScore){
                    return false;
                }
                return score >= minScore && score <= maxScore;
        }
        return false;

    }

    public String getSymbol() {
        return symbol;
    }

    public String getRemark() {
        return remark;
    }
}
