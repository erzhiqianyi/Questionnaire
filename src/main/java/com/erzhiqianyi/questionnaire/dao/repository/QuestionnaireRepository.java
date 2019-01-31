package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionnaireRepository  extends JpaRepository<Questionnaire,Long> {
    Optional<Questionnaire> findByCodeOrTitle(String code,String title);
}
