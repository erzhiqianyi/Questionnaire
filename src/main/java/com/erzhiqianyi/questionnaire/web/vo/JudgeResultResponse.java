package com.erzhiqianyi.questionnaire.web.vo;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JudgeResultResponse {
    private Long id;
    private String msg;

    public JudgeResultResponse(JudgeLogic judgeLogic) {
        if (null != judgeLogic)   {
            this.id = judgeLogic.getId();
            this.msg= judgeLogic.getMessage();
        }
    }
}
