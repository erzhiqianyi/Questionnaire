package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answer_group_logic")
@Getter
@Setter
@DynamicUpdate
public class AnswerGroupLogic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long questionnaireId;

    private String answerGroupCode;

    private Integer minScore;

    private Integer maxScore;

    private String remark;

    @Enumerated(EnumType.STRING)
    private LogicSymbol symbol;


}
