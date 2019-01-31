package com.erzhiqianyi.questionnaire.dao.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "judge_logic")
@Getter
@Setter
@DynamicUpdate
public class JudgeLogic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionnaireId;

    private Integer score;

    private LogicSymbol symbol;

    private String message;

}
