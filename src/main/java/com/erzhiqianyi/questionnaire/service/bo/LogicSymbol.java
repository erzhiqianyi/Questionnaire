package com.erzhiqianyi.questionnaire.service.bo;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public enum LogicSymbol implements CheckJudgeInfo {
    GREATER("GT", "大于") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo &&
                    null != judgeInfo.getMaxScore() &&
                    null == judgeInfo.getMinScore();
        }
    },
    GREATER_OR_EQUALS("GTEQ", "大于等于") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo && null != judgeInfo.getMaxScore() && null == judgeInfo.getMinScore();
        }
    },
    EQUALS("EQ", "等于") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            Double max = judgeInfo.getMaxScore();
            Double min = judgeInfo.getMinScore();
            return null != judgeInfo &&
                    (null != max && null != min ?
                            Double.compare(max, min) == 0 :
                            null != max || null != min);
        }
    },
    LESS("LT", "小于") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo && null != judgeInfo.getMinScore() && null == judgeInfo.getMaxScore();
        }
    },
    LESS_OR_EQUALS("LTEQ", "小于等于") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo && null != judgeInfo.getMinScore() && null == judgeInfo.getMaxScore();
        }
    },
    BETWEEN("BT", "之间，开区间") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo &&
                    null != judgeInfo.getMaxScore() &&
                    null != judgeInfo.getMinScore() &&
                    Double.compare(judgeInfo.getMaxScore(), judgeInfo.getMinScore()) == 1;
        }
    },
    BETWEEN_CLOSE("BTC", "之间闭区间") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo &&
                    null != judgeInfo.getMaxScore() &&
                    null != judgeInfo.getMinScore() &&
                    Double.compare(judgeInfo.getMaxScore(), judgeInfo.getMinScore()) == 1;
        }
    },
    BETWEEN_R_CLOSE("BTRC", "左开右闭") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo &&
                    null != judgeInfo.getMaxScore() &&
                    null != judgeInfo.getMinScore() &&
                    Double.compare(judgeInfo.getMaxScore(), judgeInfo.getMinScore()) == 1;
        }
    },
    BETWEEN_L_CLOSE("BTLC", "左闭右开") {
        @Override
        public boolean checkJudgeInfo(JudgeInfo judgeInfo) {
            return null != judgeInfo &&
                    null != judgeInfo.getMaxScore() &&
                    null != judgeInfo.getMinScore() &&
                    Double.compare(judgeInfo.getMaxScore(), judgeInfo.getMinScore()) == 1;
        }
    },

    ;
    private String symbol;
    private String remark;

    public static final String NO_RESULT = "没有结果";


    LogicSymbol(String symbol, String remark) {
        this.symbol = symbol;
        this.remark = remark;
    }

    public static boolean judgeInfo(JudgeInfo judgeInfo, Double score) {
        if (null == judgeInfo) {
            return false;
        }

        if (null == score) {
            return false;
        }

        LogicSymbol logic = judgeInfo.getSymbol();
        if (null == logic) {
            return false;
        }

        if (!logic.checkJudgeInfo(judgeInfo)) {
            return false;
        }

        Double minScore = judgeInfo.getMinScore();
        Double maxScore = judgeInfo.getMaxScore();

        int compareMin = null != minScore ? Double.compare(score, minScore) : -1;
        int compareMax = null != maxScore ? Double.compare(score, maxScore) : -1;
        switch (logic) {
            case LESS:
                return compareMin == -1;
            case LESS_OR_EQUALS:
                return compareMin == -1 || compareMin == 0;
            case EQUALS:
                var minEqual = false;
                var maxEqual = false;
                if (null != minScore) {
                    minEqual = compareMin == 0;
                }
                if (null != maxScore) {
                    maxEqual = compareMax == 0;
                }
                return minEqual || maxEqual;
            case GREATER_OR_EQUALS:
                return compareMax == 1 || compareMax == 0;
            case GREATER:
                return compareMax == 1;
            case BETWEEN:
                return compareMin == 1 && compareMax == -1;
            case BETWEEN_CLOSE:
                return (compareMin == 1 || compareMin == 0)
                        && (compareMax == -1 || compareMax == 0);
            case BETWEEN_R_CLOSE:
                return compareMin == 1 && (compareMax == -1 || compareMax == 0);
            case BETWEEN_L_CLOSE:
                return (compareMin == 1 || compareMin == 0) && (compareMax == -1);

        }
        return false;

    }

    public static JudgeInfo judgeBetter(JudgeInfo one, JudgeInfo another) {
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

    public static JudgeInfo judgeBetterNotSame(JudgeInfo one, JudgeInfo another) {
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
                    case BETWEEN_R_CLOSE:
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
                    case BETWEEN_R_CLOSE:
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

    public static JudgeInfo judgeBetterBySame(JudgeInfo one, JudgeInfo another) {
        if (!one.getSymbol().checkJudgeInfo(one) || !another.getSymbol().checkJudgeInfo(another)) {
            return null;
        }

        if (one.getSymbol() != another.getSymbol()) {
            return null;
        }
        Double oneMin = one.getMinScore();
        Double anotherMin = another.getMinScore();
        Double oneMax = one.getMaxScore();
        Double anotherMax = another.getMaxScore();
        int minCompare = -1;
        if (null != oneMin && null != anotherMin) {
            minCompare = Double.compare(oneMin, anotherMin);
        }
        int maxCompare = -1;
        if (null != oneMax && null != anotherMax) {
            maxCompare = Double.compare(oneMax, anotherMax);
        }

        switch (one.getSymbol()) {
            case LESS:
                return minCompare == 1 ? another : one;
            case LESS_OR_EQUALS:
                return minCompare == 1 || minCompare == 0 ? another : one;
            case GREATER_OR_EQUALS:
                return maxCompare == 1 || maxCompare == 0 ? one : another;
            case GREATER:
                return maxCompare == 1 ? one : another;
            case BETWEEN:
            case BETWEEN_CLOSE:
            case BETWEEN_L_CLOSE:
            case BETWEEN_R_CLOSE:
                switch (minCompare) {
                    case -1:
                        return maxCompare == 1 || maxCompare == 0 ? another : null;
                    case 0:
                        return maxCompare == 1 ? another : one;
                    case 1:
                        return maxCompare == -1 || maxCompare == 0 ? one : null;
                }


        }
        return null;
//        switch (one.getSymbol()) {
//            case LESS:
//                if (minCompare == -1) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case LESS_OR_EQUALS:
//                if (minCompare == -1 || minCompare == 0) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case EQUALS:
//                return one;
//            case GREATER_OR_EQUALS:
//                if (oneMaxCompareAnother == 1 || oneMaxCompareAnother == 0) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case GREATER:
//                if (oneMaxCompareAnother == 1) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case BETWEEN:
//                if (minCompare == 1 && oneMaxCompareAnother == 1) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case BETWEEN_CLOSE:
//                if ((oneMin == 1 || oneMin == 0) &&
//                        (oneMaxCompareAnother == 1 || oneMaxCompareAnother == 0)) {
//                    return one;
//                } else {
//                    return another;
//                }
//            case BETWEEN_R_CLOSE:
//                if (minCompare == 1 && oneMaxCompareAnother == 1 || oneMaxCompareAnother == 0) {
//                    return one;
//                } else {
//                    return another;
//                }
//            default:
//                return null;
//        }
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

    public Optional<JudgeInfo> judgeScore(Double score, List<JudgeInfo> judgeInfos) {
        JudgeLogic judgeLogicResult = null;
        for (JudgeInfo judgeLogic : judgeInfos) {
            boolean isInLogic = LogicSymbol.judgeInfo(judgeLogic, score);
            if (isInLogic) {
                if (null == judgeLogicResult) {
//                    judgeLogicResult = judgeLogic;
                } else {
//                    judgeLogicResult = LogicSymbol.judgeBetter(judgeLogic, judgeLogicResult, score);
                }
            }
        }
        if (null == judgeLogicResult) {
            return Optional.empty();
        }
//        return Optional.of(judgeLogicResult);
        return Optional.empty();
    }

}
