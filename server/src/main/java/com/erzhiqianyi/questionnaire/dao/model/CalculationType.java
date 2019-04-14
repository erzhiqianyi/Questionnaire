package com.erzhiqianyi.questionnaire.dao.model;


import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public enum CalculationType {
    SUM("sum", "求和"),
    AVG("avg", "平均值"),
    COUNT("count", "求数量");

    private String operate;
    private String remark;

    CalculationType(String operate, String remark) {
        this.operate = operate;
        this.remark = remark;
    }

    public static  CalculationType operate(String code){
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return Arrays.asList(values()).stream()
                .filter(type -> type.operate.equals(code))
                .findFirst().get();
    }
}

