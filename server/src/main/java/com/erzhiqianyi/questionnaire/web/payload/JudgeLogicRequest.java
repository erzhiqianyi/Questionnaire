package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLevel;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Log4j2
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

    private String groupCode;

    private String groupName;


    public JudgeLogicRequest() {
    }

    public JudgeLogicRequest(String[] item,String questionGroupName) {

        if (null == item || item.length != 6) {
            throw new IllegalArgumentException(" illegal csv data");
        }
        this.groupCode = item[0].replace(" ", "").toUpperCase();
        this.groupName = questionGroupName;
        this.symbol = LogicSymbol.symbol(item[1].replace(" ", ""));
        this.level = JudgeLevel.valueOf(item[2].replace(" ",""));
        this.message = item[3];
        if (!StringUtils.isEmpty(item[4])) {
            this.minScore = Double.parseDouble(item[4].replace(" ", ""));
        }
        if (!StringUtils.isEmpty(item[5]) ) {
            String temp = item[5].replace(" ", "");
            if (!StringUtils.isEmpty(temp)){
                this.maxScore = Double.parseDouble(temp);
            }
        }



    }
}
