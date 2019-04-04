package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.AnswerGroupLogic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerGroupLogicRepository extends JpaRepository<AnswerGroupLogic,Long> {
    List<AnswerGroupLogic> findByQuestionnaireId(Long questionnaireId);
}
