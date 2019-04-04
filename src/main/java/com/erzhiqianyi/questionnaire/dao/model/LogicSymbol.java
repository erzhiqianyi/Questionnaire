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

        Integer minScore = judgeLogic.getMinScore();
        Integer maxScore = judgeLogic.getMaxScore();
        switch (logic) {
            case LESS:
                return score < minScore;
            case LESS_OR_EQUALS:
                return score <= minScore;
            case EQUALS:
                var minEqual = false;
                var maxEqual = false;
                if (null != minScore) {
                    minEqual = score == minScore;
                }
                if (null != maxScore) {
                    maxEqual = score == maxScore;
                }
                return minEqual || maxEqual;
            case GREATER_OR_EQUALS:
                return score >= maxScore;
            case GREATER:
                return score > maxScore;
            case BETWEEN:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return score > minScore && score < maxScore;
            case BETWEEN_CLOSE:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return score >= minScore && score <= maxScore;
            case BETEEN_R_CLOSE:
                if (null == minScore || null == maxScore) {
                    return false;
                }
                return score > minScore && score <= maxScore;
        }
        return false;

    }

    public static JudgeLogic judgeBetter(JudgeLogic one, JudgeLogic another, Integer score) {
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
            return judgeBetterNotSame(one, another, score);
        }
    }

    private static JudgeLogic judgeBetterNotSame(JudgeLogic one, JudgeLogic another, Integer score) {
        Integer oneMinScore = one.getMinScore();
        Integer anotherMinScore = another.getMinScore();
        Integer oneMaxScore = one.getMaxScore();
        Integer anotherMaxScore = another.getMaxScore();
        LogicSymbol oneSymbol = one.getSymbol();
        LogicSymbol anotherSymbol = another.getSymbol();
        switch (oneSymbol) {
            case LESS:
            case LESS_OR_EQUALS:
                switch (anotherSymbol) {
                    case LESS:
                    case LESS_OR_EQUALS:
                        if (oneMinScore < anotherMinScore) {
                            return one;
                        } else {
                            return another;
                        }
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                        if (oneMinScore < anotherMinScore) {
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
                        if (oneMaxScore > anotherMaxScore) {
                            return one;
                        } else {
                            return another;
                        }
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                        if (oneMaxScore > anotherMaxScore) {
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
                        if (oneMinScore < anotherMinScore) {
                            return one;
                        } else {
                            return another;
                        }
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETEEN_R_CLOSE:
                        if (oneMinScore >= anotherMinScore && oneMaxScore <= anotherMaxScore) {
                            return one;
                        } else {
                            return another;
                        }
                    case GREATER:
                    case GREATER_OR_EQUALS:
                        if (oneMaxScore < anotherMaxScore) {
                            return one;
                        } else {
                            return another;
                        }
                }
        }

        return null;

    }

    private static JudgeLogic judgeBetterBySame(JudgeLogic one, JudgeLogic another) {
        Integer oneMinScore = one.getMinScore();
        Integer anotherMinScore = another.getMinScore();
        Integer oneMaxScore = one.getMaxScore();
        Integer anotherMaxScore = another.getMaxScore();
        switch (one.getSymbol()) {
            case LESS:
                if (oneMinScore < anotherMinScore) {
                    return one;
                } else {
                    return another;
                }
            case LESS_OR_EQUALS:
                if (oneMinScore <= anotherMinScore) {
                    return one;
                } else {
                    return another;
                }
            case EQUALS:
                return one;
            case GREATER_OR_EQUALS:
                if (oneMaxScore >= anotherMaxScore) {
                    return one;
                } else {
                    return another;
                }
            case GREATER:
                if (oneMaxScore > anotherMaxScore) {
                    return one;
                } else {
                    return another;
                }
            case BETWEEN:
                if (oneMinScore > anotherMinScore && oneMaxScore < anotherMaxScore) {
                    return one;
                } else {
                    return another;
                }
            case BETWEEN_CLOSE:
                if (oneMinScore >= anotherMinScore && oneMaxScore <= anotherMaxScore) {
                    return one;
                } else {
                    return another;
                }
            case BETEEN_R_CLOSE:
                if (oneMinScore > anotherMinScore && oneMaxScore <= anotherMaxScore) {
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
