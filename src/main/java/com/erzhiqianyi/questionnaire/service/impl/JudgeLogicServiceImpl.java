package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.dao.model.LogicSymbol;
import com.erzhiqianyi.questionnaire.dao.repository.JudgeLogicRepository;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class JudgeLogicServiceImpl implements JudgeLogicService {

    @Autowired
    private JudgeLogicRepository judgeLogicRepository;

    @Override
    public Optional<JudgeLogic> judgeScore(Integer score, Long questionnaireId) {
        if (null == score) {
            return Optional.empty();
        }
        if (null == questionnaireId) {
            return Optional.empty();
        }
        List<JudgeLogic> judgeLogics = judgeLogicRepository.findByQuestionnaireId(questionnaireId);
        if (null == judgeLogics || judgeLogics.isEmpty()) {
            return Optional.empty();
        }
        return judgeScore(score, judgeLogics);
    }

    @Override
    public Optional<JudgeLogic> judgeScore(Integer score, List<JudgeLogic> judgeLogics) {
        judgeLogics.sort(Comparator.comparing(JudgeLogic::getMinScore));
        boolean isJudge = true;
        JudgeLogic judgeLogicResult = null;
        for (JudgeLogic judgeLogic : judgeLogics) {
            isJudge = LogicSymbol.judgeLogic(judgeLogic, score);
            if (!isJudge) {
                judgeLogicResult = judgeLogic;
                break;
            }
        }
        return Optional.of(judgeLogicResult);
    }
}
