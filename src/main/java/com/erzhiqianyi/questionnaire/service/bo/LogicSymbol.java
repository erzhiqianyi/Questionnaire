package com.erzhiqianyi.questionnaire.service.bo;

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

    public  static <T extends JudgeInfo>  T judgeBetter(T one, T another) {
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

    public static  <T extends JudgeInfo> T  judgeBetterNotSame(T one, T another) {
        if (!one.getSymbol().checkJudgeInfo(one) || !another.getSymbol().checkJudgeInfo(another)) {
            return null;
        }

        if (one.getSymbol() == another.getSymbol()) {
            return null;
        }

        Double oneMin = one.getMinScore();
        Double anotherMin = another.getMinScore();

        Double oneMax = one.getMaxScore();
        Double anotherMax = another.getMaxScore();


        LogicSymbol oneSymbol = one.getSymbol();
        LogicSymbol anotherSymbol = another.getSymbol();

        int minCompare = -1;
        if (null != oneMin && null != anotherMin) {
            minCompare = Double.compare(oneMin, anotherMin);
        }

        int maxCompare = -1;

        if (null != oneMax && null != anotherMax) {
            maxCompare = Double.compare(oneMax, anotherMax);
        }

        switch (oneSymbol) {
            case LESS:
                switch (anotherSymbol) {
                    case LESS_OR_EQUALS:
                        return minCompare == -1 || minCompare == 0 ? one : another;
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETWEEN_L_CLOSE:
                    case BETWEEN_R_CLOSE:
                        int rightCompare = Double.compare(anotherMax, oneMin);
                        switch (rightCompare) {
                            case -1:
                                return another;
                            default:
                                return null;
                        }
                    default:
                        return null;
                }
            case LESS_OR_EQUALS:
                switch (anotherSymbol) {
                    case LESS:
                        return minCompare == -1 ? one : another;
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_L_CLOSE:
                    case BETWEEN_R_CLOSE:
                    case BETWEEN_CLOSE:
                        int rightCompare = Double.compare(anotherMax, oneMin);
                        switch (rightCompare) {
                            case -1:
                            case 0:
                                return another;
                            default:
                                return null;
                        }
                    default:
                        return null;
                }
            case EQUALS:
                switch (anotherSymbol) {
                    case LESS:
                        oneMin = null == oneMin ? oneMax : oneMin;
                        minCompare = Double.compare(oneMin, anotherMin);
                        if (minCompare == 1 || minCompare == 0) {
                            return null;
                        }
                        return one;
                    case LESS_OR_EQUALS:
                        oneMin = null == oneMin ? oneMax : oneMin;
                        minCompare = Double.compare(oneMin, anotherMin);
                        if (minCompare == 1) {
                            return null;
                        }
                        return one;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETWEEN_R_CLOSE:
                    case BETWEEN_L_CLOSE:
                        oneMin = null == oneMin ? oneMax : oneMin;
                        minCompare = Double.compare(oneMin, anotherMin);
                        maxCompare = Double.compare(oneMin, anotherMax);
                        if ((minCompare == 1 || minCompare == 0) &&
                                (maxCompare == -1 || maxCompare == 0)) {
                            return one;
                        } else {
                            return null;
                        }
                    case GREATER:
                        oneMax = null == oneMax ? oneMin : oneMax;
                        minCompare = Double.compare(oneMax, anotherMax);
                        if (minCompare == -1 || minCompare == 0) {
                            return null;
                        }
                        return one;
                    case GREATER_OR_EQUALS:
                        oneMax = null == oneMax ? oneMin : oneMax;
                        minCompare = Double.compare(oneMax, anotherMax);
                        if (minCompare == -1) {
                            return null;
                        }
                        return one;
                    default:
                        return one;

                }
            case GREATER_OR_EQUALS:
                switch (anotherSymbol) {
                    case GREATER:
                        return maxCompare == 1 ? one : another;
                    case EQUALS:
                        return another;
                    case BETWEEN:
                    case BETWEEN_L_CLOSE:
                    case BETWEEN_R_CLOSE:
                    case BETWEEN_CLOSE:
                        int leftCompare = Double.compare(anotherMin, oneMax);
                        switch (leftCompare) {
                            case 0:
                            case 1:
                                return another;
                            default:
                                return null;
                        }
                    default:
                        return null;
                }
            case GREATER:
                switch (anotherSymbol) {
                    case EQUALS:
                        return another;
                    case GREATER_OR_EQUALS:
                        return maxCompare == 1 ? one : another;
                    case BETWEEN:
                    case BETWEEN_CLOSE:
                    case BETWEEN_R_CLOSE:
                        int leftCompare = Double.compare(anotherMin, oneMax);
                        switch (leftCompare) {
                            case 0:
                            case 1:
                                return another;
                            default:
                                return null;
                        }
                    default:
                        return null;
                }
            case BETWEEN:
            case BETWEEN_CLOSE:
            case BETWEEN_R_CLOSE:
            case BETWEEN_L_CLOSE:
                switch (anotherSymbol) {
                    case LESS:
                    case LESS_OR_EQUALS:
                        maxCompare = Double.compare(oneMax, anotherMin);
                        if (maxCompare == -1 || maxCompare == 0) {
                            return one;
                        } else {
                            return null;
                        }
                    case EQUALS:
                        return another;
                    case GREATER:
                    case GREATER_OR_EQUALS:
                        minCompare = Double.compare(oneMin, anotherMax);
                        if (minCompare == 1 || minCompare == 0) {
                            return one;
                        } else {
                            return null;
                        }
                    case BETWEEN_L_CLOSE:
                    case BETWEEN_R_CLOSE:
                    case BETWEEN_CLOSE:
                    case BETWEEN:
                        if (( minCompare == 1) &&
                                (maxCompare == -1 )) {
                            return one;
                        } else if ( (  minCompare == -1 ) &&
                                (maxCompare == 1 )  ){
                            return another;
                        }else {
                            return null;
                        }
                    default:
                        return null;
                }

        }
        return null;

    }

    public static  <T extends JudgeInfo> T judgeBetterBySame(T one, T another) {
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

    public static <T extends JudgeInfo> Optional<T>  judgeScore(Double score, List<T> judgeInfos) {
        T judgeLogicResult = null;

        for (T judgeInfo : judgeInfos) {
            boolean isInLogic = LogicSymbol.judgeInfo(judgeInfo, score);
            if (isInLogic) {
                if (null == judgeLogicResult) {
                    judgeLogicResult = judgeInfo;
                } else {
                    judgeLogicResult = LogicSymbol.judgeBetter(judgeInfo,judgeLogicResult);
                }
            }
        }
        if (null == judgeLogicResult) {
            return Optional.empty();
        }
        return Optional.of(judgeLogicResult);
    }



}
