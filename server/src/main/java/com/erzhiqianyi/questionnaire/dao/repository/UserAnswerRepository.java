package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer,Long> {
    List<UserAnswer> findByUserQuestionnaireId(Long userQuestionnaireId );
}
