package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "questionnaire_question")
@Getter
@Setter
@DynamicUpdate
public class QuestionnaireQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionId;

    private Long questionnaireId;


}
