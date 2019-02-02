package com.erzhiqianyi.questionnaire.service;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;

import java.util.List;
import java.util.Optional;

public interface JudgeLogicService {
    Optional<JudgeLogic> judgeScore(Integer score, Long questionnaireId);

    Optional<JudgeLogic> judgeScore(Integer score, List<JudgeLogic> judgeLogics);

    Optional<JudgeLogic> getJudgeLogic(Long judgeLogicId);

}
