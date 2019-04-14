package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question,Long> {
    List<Question> findByQuestionnaireId(Long questionnaireId);
}
