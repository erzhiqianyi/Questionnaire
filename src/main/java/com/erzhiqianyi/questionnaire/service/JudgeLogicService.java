package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;

import java.util.List;
import java.util.Optional;

public interface JudgeLogicService {

    Optional<JudgeLogic> getJudgeLogic(Long judgeLogicId);

    List<JudgeLogic> getQuestionnaireJudgeLogic(Long questionnaireId);

}
