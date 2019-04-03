package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.LogicSymbol;
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
    private Integer minScore;

    private Integer maxScore;

    @NotNull
    private LogicSymbol symbol;

    @NotNull
    @Size(max = 1000)
    private String message;

    private String questionGroupCode;

    public JudgeLogicRequest() {
    }

    public JudgeLogicRequest(String[] item) {
        if (null == item || item.length != 5) {
            throw new IllegalArgumentException(" illegal csv data");
        }
        this.questionGroupCode = item[0].replace(" ", "").toUpperCase();
        this.symbol = LogicSymbol.symbol(item[1].replace(" ", ""));
        this.message = item[2];
        if (!StringUtils.isEmpty(item[3])) {
            this.minScore = Integer.parseInt(item[3].replace(" ", ""));
        }
        if (!StringUtils.isEmpty(item[4])) {
            this.maxScore = Integer.parseInt(item[4].replace(" ", ""));
        }

    }
}
