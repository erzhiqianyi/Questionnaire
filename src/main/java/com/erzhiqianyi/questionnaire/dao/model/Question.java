package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Getter
@Setter
@DynamicUpdate
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionnaireId;

    private String content;

    private QuestionType type;

    private Boolean required;

    private Integer answerCount;

    private Integer sort;

}
