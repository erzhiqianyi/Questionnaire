package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.JudgeLogic;
import com.erzhiqianyi.questionnaire.service.bo.LogicSymbol;
import com.erzhiqianyi.questionnaire.dao.repository.JudgeLogicRepository;
import com.erzhiqianyi.questionnaire.service.JudgeLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JudgeLogicServiceImpl implements JudgeLogicService {

    @Autowired
    private JudgeLogicRepository judgeLogicRepository;


    @Override
    public Optional<JudgeLogic> getJudgeLogic(Long judgeLogicId) {
        if (null == judgeLogicId) {
            return Optional.empty();
        }

        return judgeLogicRepository.findById(judgeLogicId);
    }

    @Override
    public List<JudgeLogic> getQuestionnaireJudgeLogic(Long questionnaireId) {
        if(null == questionnaireId){
            return new ArrayList<>();
        }
        return judgeLogicRepository.findByQuestionnaireId(questionnaireId);
    }
}
