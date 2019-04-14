package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.JudgeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface JudgeResultRepository extends JpaRepository<JudgeResult, Long> {
    List<JudgeResult> findByUserQuestionnaireId(Long id);
}
