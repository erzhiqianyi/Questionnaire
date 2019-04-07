package com.erzhiqianyi.questionnaire.dao.model;

import org.springframework.util.StringUtils;

public enum LogicSymbol {
    GREATER("GT", "大于"),
    GREATER_OR_EQUALS("GTEQ", "大于等于"),
    EQUALS("EQ", "等于"),
    LESS("LT", "小于"),
    LESS_OR_EQUALS("LTEQ", "小于等于"),
    BETWEEN("BT", "之间，开区间"),
    BETWEEN_CLOSE("BTC", "之间闭区间"),
    BETEEN_R_CLOSE("BTRC", "左开右闭");
    private String symbol;
    private String remark;

    public static final String NO_RESULT = "没有结果";

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

        Double minScore = judgeLogic.getMinScore();
        Double maxScore = judgeLogic.getMaxScore();
        int scoreCompareMinScore = Double.compare(score, minScore);
        int scoreComapreMaxScore = Double.compare(score, maxScore);
        switch (logic) {
            case LESS:
                return scoreCompareMinScore == -1;
            case LESS_OR_EQUALS:
                return scoreCompareMinScore == -1 || score == 0;
            case EQUALS:
                var minEqual = false;
                var maxEqual = false;
                if (null != minScore) {
                    minEqual = scoreCompareMinScore == 0;
                }
                if (null != maxScore) {
                    maxEqual = scoreComapreMaxScore == 0;
                }
                return minEqual || maxEqual;
            case GREATER_OR_EQUALS:
                return scoreComapreMaxScore == 1 || scoreComapreMaxScore == 0;
            case GREATER:
                return scoreComapreMaxScore == 1;
            case BETWEEN:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return scoreCompareMinScore == 1 && scoreComapreMaxScore == -1;
            case BETWEEN_CLOSE:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return (scoreCompareMinScore == 1 || scoreComapreMaxScore == 0)
                        && (scoreComapreMaxScore == -1 || scoreComapreMaxScore == 0);
            case BETEEN_R_CLOSE:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return scoreCompareMinScore == 1 && (scoreComapreMaxScore == -1 || scoreComapreMaxScore == 0);
        }
        return false;

    }

    public static JudgeLogic judgeBetter(JudgeLogic one, JudgeLogic another) {
        if (null == one && null == another) {
            return null;
        }
        if (null == one) {
            return another;
        }
        if (null == another) {
            return one;
        }
        LogicSymbol oneSymbol = one.getSymbol();
        LogicSymbol anotherSymbol = another.getSymbol();
        if (oneSymbol == anotherSymbol) {
            return judgeBetterBySame(one, another);
        } else {
            return judgeBetterNotSame(one, another);
        }
    }

    private static JudgeLogic judgeBetterNotSame(JudgeLogic one, JudgeLogic another) {
        Double oneMinScore = one.getMinScore();
        Double anotherMinScore = another.getMinScore();
        Double oneMaxScore = one.getMaxScore();
        Double anotherMaxScore = another.getMaxScore();
        LogicSymbol oneSymbol = one.getSymbol();
        LogicSymbol anotherSymbol = another.getSymbol();
        int oneMinCompareAnother = Double.compare(oneMinScore, anotherMinScore);
        int oneMaxComapreAnother = Double.compare(oneMaxScore, anotherMaxScore);
        switch (oneSymbol) {
            case LESS:
            case LESS_OR_EQUALS:
                switch (anotherSymbol) {
                    case LESS:
                    case LESS_OR_EQUALS:
                        if (oneMinCompareAnother == 0 || oneMinCompareAnother == -1) {
                            return one;
                        } else {
                            return another;
                        }
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                        if (oneMinCompareAnother == -1) {
                            return one;
                        } else {
                            return another;
                        }
                }
            case EQUALS:
                return one;
            case GREATER_OR_EQUALS:
            case GREATER:
                switch (anotherSymbol) {
                    case EQUALS:
                        return another;
                    case GREATER:
                    case GREATER_OR_EQUALS:
                        if (oneMaxComapreAnother == 1 || oneMaxComapreAnother == 0) {
                            return one;
                        } else {
                            return another;
                        }
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETEEN_R_CLOSE:
                        if (oneMaxComapreAnother == 1 || oneMaxComapreAnother == 0) {
                            return one;
                        } else {
                            return another;
                        }
                }
            case BETWEEN:
            case BETWEEN_CLOSE:
                switch (anotherSymbol) {
                    case LESS:
                    case LESS_OR_EQUALS:
                        if (oneMinCompareAnother == -1) {
                            return one;
                        } else {
                            return another;
                        }
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETEEN_R_CLOSE:
                        if ((oneMinCompareAnother == 0 || oneMaxComapreAnother == 1) &&
                                (oneMaxComapreAnother == -1 || oneMaxComapreAnother == 0)) {
                            return one;
                        } else {
                            return another;
                        }
                    case GREATER:
                    case GREATER_OR_EQUALS:
                        if (oneMaxComapreAnother == -1) {
                            return one;
                        } else {
                            return another;
                        }
                }
        }
        return null;

    }

    private static JudgeLogic judgeBetterBySame(JudgeLogic one, JudgeLogic another) {
        Double oneMinScore = one.getMinScore();
        Double anotherMinScore = another.getMinScore();
        Double oneMaxScore = one.getMaxScore();
        Double anotherMaxScore = another.getMaxScore();
        int oneMinCompareAnother = Double.compare(oneMinScore, anotherMinScore);
        int oneMaxComapareAnother = Double.compare(oneMaxScore, anotherMaxScore);
        switch (one.getSymbol()) {
            case LESS:
                if (oneMinCompareAnother == -1) {
                    return one;
                } else {
                    return another;
                }
            case LESS_OR_EQUALS:
                if (oneMinCompareAnother == -1 || oneMinCompareAnother == 0) {
                    return one;
                } else {
                    return another;
                }
            case EQUALS:
                return one;
            case GREATER_OR_EQUALS:
                if (oneMaxComapareAnother == 1 || oneMaxComapareAnother == 0) {
                    return one;
                } else {
                    return another;
                }
            case GREATER:
                if (oneMaxComapareAnother == 1) {
                    return one;
                } else {
                    return another;
                }
            case BETWEEN:
                if (oneMinCompareAnother == 1 && oneMaxComapareAnother == 1) {
                    return one;
                } else {
                    return another;
                }
            case BETWEEN_CLOSE:
                if ((oneMinScore == 1 || oneMinScore == 0) &&
                        (oneMaxComapareAnother == 1 || oneMaxComapareAnother == 0)) {
                    return one;
                } else {
                    return another;
                }
            case BETEEN_R_CLOSE:
                if (oneMinCompareAnother == 1 && oneMaxComapareAnother == 1 || oneMaxComapareAnother == 0) {
                    return one;
                } else {
                    return another;
                }
            default:
                return null;
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public String getRemark() {
        return remark;
    }

    public static LogicSymbol symbol(String symbol) {
        if (StringUtils.isEmpty(symbol)) {
            return null;
        }
        for (LogicSymbol logic : values()) {
            if (logic.symbol.equals(symbol)) {
                return logic;
            }
        }
        return null;
    }
}
