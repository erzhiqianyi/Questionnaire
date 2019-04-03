package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.dao.model.LogicSymbol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
