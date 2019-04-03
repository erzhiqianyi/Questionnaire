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

    private Long questionGroupId;

    private String groupCode;

    private String groupName;

    private Integer score;

    private Integer percent;

    private Integer status;

    private String remark;

    private Long judgeLogicId;

}
