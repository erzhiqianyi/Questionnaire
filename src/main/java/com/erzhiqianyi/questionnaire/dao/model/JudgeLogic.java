package com.erzhiqianyi.questionnaire.dao.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "question_answer")
@Getter
@Setter
@DynamicUpdate
public class JudgeLogic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer score;

    private LogicSymbol symbol;

    private String message;
}
