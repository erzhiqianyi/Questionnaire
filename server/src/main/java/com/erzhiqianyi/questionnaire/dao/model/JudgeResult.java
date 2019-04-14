package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "judge_result")
@Getter
@Setter
@DynamicUpdate
public class JudgeResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String groupCode;

    private String groupName;

    private Integer totalScore;

    private Integer totalCount;

    private Double averageScore;

    private Double percent;

    @Enumerated(EnumType.STRING)
    private JudgeLevel level;

    @Column(length = 1000)
    private String suggestion;

    private Long judgeLogicId;

    private Long userQuestionnaireId;

    @Enumerated(EnumType.STRING)
    private CalculationType calculationType;

}
