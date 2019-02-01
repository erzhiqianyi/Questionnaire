package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.UserQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestionnaireRepository   extends JpaRepository<UserQuestionnaire,Long> {
}
