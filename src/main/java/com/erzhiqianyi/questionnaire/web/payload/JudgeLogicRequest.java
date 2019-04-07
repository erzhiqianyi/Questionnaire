package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLevel;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class JudgeLogicRequest {
    private Double minScore;

    private Double maxScore;

    @NotNull
    private LogicSymbol symbol;

    @NotNull
    private JudgeLevel level;

    @NotNull
    @Size(max = 1000)
    private String message;

    private String questionGroupCode;

    private String questionGroupName;


    public JudgeLogicRequest() {
    }

    public JudgeLogicRequest(String[] item,String questionGroupName) {
        if (null == item || item.length != 6) {
            throw new IllegalArgumentException(" illegal csv data");
        }
        this.questionGroupCode = item[0].replace(" ", "").toUpperCase();
        this.questionGroupName = questionGroupName;
        this.symbol = LogicSymbol.symbol(item[1].replace(" ", ""));
        this.level = JudgeLevel.valueOf(item[2].replace(" ",""));
        this.message = item[3];
        if (!StringUtils.isEmpty(item[4])) {
            this.minScore = Double.parseDouble(item[4].replace(" ", ""));
        }
        if (!StringUtils.isEmpty(item[5])) {
            this.maxScore = Double.parseDouble(item[5].replace(" ", ""));
        }



    }
}
