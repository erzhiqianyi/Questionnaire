package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JudgeLogicRepository extends JpaRepository<JudgeLogic, Long> {
    List<JudgeLogic> findByQuestionnaireId(Long questionnaireId);
    List<JudgeLogic> findByQuestionnaireIdAndQuestionGroupCode(Long questionnaireId,String  questionGroupCode);
}
