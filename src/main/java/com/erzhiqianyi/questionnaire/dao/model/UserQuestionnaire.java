package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user_questionnaire")
@Getter
@Setter
@DynamicUpdate
public class UserQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  Long questionnaireId;

    private Long userId;

    private Integer totalScore;

    private Long judgeLogicId;

}
