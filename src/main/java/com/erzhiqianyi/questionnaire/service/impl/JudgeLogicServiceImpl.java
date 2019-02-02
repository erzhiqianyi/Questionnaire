package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.dao.model.LogicSymbol;
import com.erzhiqianyi.questionnaire.dao.repository.JudgeLogicRepository;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        JudgeLogic judgeLogicResult = null;
        for (JudgeLogic judgeLogic : judgeLogics ){
            boolean isInLogic  = LogicSymbol.judgeLogic(judgeLogic,score);
            if (isInLogic){
                if (null == judgeLogicResult){
                    judgeLogicResult = judgeLogic;
                }else {
                   judgeLogicResult = LogicSymbol.judgeBetter(judgeLogic,judgeLogicResult,score);
                }
            }
        }
        if (null == judgeLogicResult){
            judgeLogicResult = new JudgeLogic();
            judgeLogicResult.setMessage(LogicSymbol.NO_RESULT);
            return Optional.of(judgeLogicResult);
        }
        return Optional.of(judgeLogicResult);
    }

    @Override
    public Optional<JudgeLogic> getJudgeLogic(Long judgeLogicId) {
        if (null == judgeLogicId){
            return Optional.empty();
        }

        return judgeLogicRepository.findById(judgeLogicId);
    }
}
