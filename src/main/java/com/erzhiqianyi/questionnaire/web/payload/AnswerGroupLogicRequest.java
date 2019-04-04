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
public class AnswerGroupLogicRequest {
    private Integer minScore;

    private Integer maxScore;

    @NotNull
    private LogicSymbol symbol;

    @NotNull
    @Size(max = 200)
    private String remark;

    private String answerGroupCode;

    private String answerGroupName;

    public AnswerGroupLogicRequest() {
    }

    public AnswerGroupLogicRequest(String[] csv) {
        if (null == csv || csv.length != 5) {
            throw new IllegalArgumentException("illegal argument ");
        }
        this.answerGroupCode = csv[0].replace(" ", "").toUpperCase();
        this.symbol = LogicSymbol.symbol(csv[1].replace(" ",""));
        this.remark = csv[2];
        if (!StringUtils.isEmpty(csv[3])){
           this.minScore = Integer.valueOf(csv[3].replace(" " ,""));
        }
        if (!StringUtils.isEmpty(csv[4])){
           this.maxScore = Integer.valueOf(csv[4].replace(" " ,"")) ;
        }
    }
}
