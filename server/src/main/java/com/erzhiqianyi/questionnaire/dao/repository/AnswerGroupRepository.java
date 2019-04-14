package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.AnswerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerGroupRepository extends JpaRepository<AnswerGroup,Long> {
    List<AnswerGroup> findByQuestionnaireId(Long questionnaireId);
}
