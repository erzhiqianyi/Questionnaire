package com.erzhiqianyi.questionnaire.dao.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "judge_logic")
@Getter
@Setter
@DynamicUpdate
@ToString
public class JudgeLogic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionnaireId;

    private Long questionGroupId;

    private Integer minScore;

    private Integer maxScore;

    @Enumerated(EnumType.STRING)
    private LogicSymbol symbol;

    private String message;

}
