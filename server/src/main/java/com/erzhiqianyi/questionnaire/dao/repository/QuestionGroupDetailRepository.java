package com.erzhiqianyi.questionnaire.dao.repository;

import com.erzhiqianyi.questionnaire.dao.model.QuestionGroupDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuestionGroupDetailRepository extends JpaRepository<QuestionGroupDetail, Long> {
    List<QuestionGroupDetail> findByQuestionGroupIdIn(Set<Long> ids);
}
