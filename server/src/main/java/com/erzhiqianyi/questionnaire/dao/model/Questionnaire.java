package com.erzhiqianyi.questionnaire.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "questionnaire")
@Getter
@Setter
@DynamicUpdate
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String code;

    @Column(length = 500)
    private String remark;

}
