package com.erzhiqianyi.questionnaire.dao.model;
import com.erzhiqianyi.questionnaire.service.bo.JudgeInfo;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
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
public class JudgeLogic implements JudgeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionnaireId;

    private String  questionGroupCode ;

    private Double minScore;

    private Double maxScore;

    @Enumerated(EnumType.STRING)
    private JudgeLevel judgeLevel;

    @Enumerated(EnumType.STRING)
    private LogicSymbol symbol;

    @Column(length = 1000)
    private String message;

}
