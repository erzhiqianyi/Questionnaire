package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "question_group")
@Getter
@Setter
@DynamicUpdate
public class QuestionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionnaireId;

    private String code;

    private String name;

    private String remark;


    @Enumerated(EnumType.STRING)
    private CalculationType collectMethod;

}
