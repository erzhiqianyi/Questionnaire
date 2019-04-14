package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionGroupRepository  extends JpaRepository<QuestionGroup, Long> {
    List<QuestionGroup> findByQuestionnaireId(Long questionnaireId);
}
