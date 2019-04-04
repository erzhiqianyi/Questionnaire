package com.erzhiqianyi.questionnaire.service.impl;

import com.erzhiqianyi.questionnaire.dao.model.*;
import com.erzhiqianyi.questionnaire.dao.repository.*;
import com.erzhiqianyi.questionnaire.dto.AnswerGroupDto;
import com.erzhiqianyi.questionnaire.dto.QuestionGroupDto;
import com.erzhiqianyi.questionnaire.service.QuestionnaireService;
import com.erzhiqianyi.questionnaire.web.payload.*;
import com.erzhiqianyi.questionnaire.web.vo.QuestionnaireResponse;
import com.erzhiqianyi.questionnaire.web.vo.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private JudgeLogicRepository judgeLogicRepository;

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    private QuestionGroupDetailRepository questionGroupDetailRepository;

    @Autowired
    private AnswerGroupLogicRepository answerGroupLogicRepository;

    @Autowired
    private AnswerGroupRepository answerGroupRepository;

    @Override
    public ResponseResult<Long> createQuestionnaire(QuestionnaireRequest request) {
        Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findByCodeOrTitle(request.getCode(), request.getTitle());
        if (optionalQuestionnaire.isPresent()) {
            return ResponseResult.badRequest(" code or title exists");
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCode(request.getCode());
        questionnaire.setTitle(request.getTitle());
        questionnaire.setRemark(request.getRemark());
        questionnaire.setHasGroup(null != request.getQuestionGroup() ? 1 : 0);
        questionnaireRepository.save(questionnaire);
        Long questionnaireId = questionnaire.getId();
        if (null == questionnaireId) {
            return ResponseResult.error(" create questionnaire fail ");
        }

        saveQuestion(request.getQuestions(), questionnaireId);

        saveQuestionGroup(request.getQuestionGroup(), questionnaireId);

        saveJudgeLogic(request.getJudgeLogic(), questionnaireId);

        saveAnswerGroup(request.getAnswerGroup(), questionnaireId);

        return ResponseResult.success("create questionnaire  success", questionnaireId);
    }

    private void saveAnswerGroup(List<AnswerGroupRequest> answerGroupRequest, Long questionnaireId) {
        if (null == answerGroupRequest || null == questionnaireId) {
            return;
        }
        var groups = answerGroupRequest.stream()
                .map(request -> {
                            var group = new AnswerGroup();
                            group.setCode(request.getCode());
                            group.setName(request.getName());
                            group.setQuestionnaireId(questionnaireId);
                            group.setCollectMethod(request.getCollectMethod());
                            group.setRemark(request.getRemark());
                            return group;
                        }
                ).collect(Collectors.toList());
        answerGroupRepository.saveAll(groups);

        var groupLogic = answerGroupRequest.stream()
                .filter(request -> null != request.getGroupLogic())
                .flatMap(request -> request.getGroupLogic().stream())
                .map(logicRequest -> {
                    var logic = new AnswerGroupLogic();
                    logic.setAnswerGroupCode(logicRequest.getAnswerGroupCode());
                    logic.setSymbol(logicRequest.getSymbol());
                    logic.setQuestionnaireId(questionnaireId);
                    logic.setMaxScore(logicRequest.getMaxScore());
                    logic.setMinScore(logicRequest.getMinScore());
                    logic.setRemark(logicRequest.getRemark());
                    return logic;
                }).collect(Collectors.toList());
        answerGroupLogicRepository.saveAll(groupLogic);
    }

    private void saveQuestionGroup(List<QuestionGroupRequest> questionGroup, Long questionnaireId) {
        if (null == questionGroup || null == questionnaireId) {
            return;
        }
        var questions = questionRepository.findByQuestionnaireId(questionnaireId);
        var questionMap = questions.stream().collect(Collectors.toMap(question -> question.getContent(), question -> question.getId()));
        questionGroup.stream()
                .forEach(request -> {
                    var group = new QuestionGroup();
                    group.setCode(request.getCode());
                    group.setName(request.getName());
                    group.setRemark(request.getRemark());
                    group.setQuestionnaireId(questionnaireId);
                    questionGroupRepository.save(group);
                    Long groupId = group.getId();
                    var details = request.getQuestions().stream().map(detailRequest -> {
                        var detail = new QuestionGroupDetail();
                        detail.setQuestionGroupId(groupId);
                        detail.setQuestionId(questionMap.get(detailRequest.getQuestionContent()));
                        return detail;
                    }).collect(Collectors.toList());
                    questionGroupDetailRepository.saveAll(details);
                });


    }

    @Override
    public ResponseResult<QuestionnaireResponse> getQuestionnaireByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return ResponseResult.badRequest("questionnaire not exists");
        }

        Optional<Questionnaire> optional = questionnaireRepository.findByCodeOrTitle(code, null);
        if (!optional.isPresent()) {
            return ResponseResult.badRequest("questionnaire not exists");
        }

        return getQuestionnaireResponse(optional.get());
    }

    @Override
    public ResponseResult<QuestionnaireResponse> getQuestionnaireById(Long questionnaireId) {
        if (null == questionnaireId) {
            return ResponseResult.badRequest("questionnaire not exists");
        }
        Optional<Questionnaire> optional = questionnaireRepository.findById(questionnaireId);
        if (!optional.isPresent()) {
            return ResponseResult.badRequest("questionnaire not exists");
        }
        return getQuestionnaireResponse(optional.get());
    }

    @Override
    public List<QuestionGroupDto> getQuestionnaireQuestionGroup(Long questionnaireId) {
        if (null == questionnaireId) {
            return Collections.emptyList();
        }
        List<QuestionGroup> groups = questionGroupRepository.findByQuestionnaireId(questionnaireId);
        if (null == groups) {
            return Collections.emptyList();
        }
        var questionGroupIds = groups.stream().map(group -> group.getId()).collect(Collectors.toSet());

        var groupDetails = questionGroupDetailRepository.findByQuestionGroupIdIn(questionGroupIds);
        var groupDetailMap = groupDetails.stream().collect(Collectors.groupingBy(QuestionGroupDetail::getQuestionGroupId));
        return groups.stream().map(group -> {
            var groupDto = new QuestionGroupDto();
            groupDto.setId(group.getId());
            groupDto.setCode(group.getCode());
            groupDto.setName(group.getName());
            groupDto.setQuestionnaireId(questionnaireId);
            groupDto.setRemark(group.getRemark());
            groupDto.setQuestions(groupDetailMap.get(group.getId())
                    .stream().map(QuestionGroupDetail::getQuestionId).collect(Collectors.toList()));
            return groupDto;
        }).collect(Collectors.toList());

    }

    @Override
    public List<AnswerGroupDto> getQuestionnaireAnswerGroup(Long questionnaireId) {
        if (null == questionnaireId) {
            return Collections.emptyList();
        }
        List<AnswerGroup> groups = answerGroupRepository.findByQuestionnaireId(questionnaireId);
        List<AnswerGroupLogic> groupLogic = answerGroupLogicRepository.findByQuestionnaireId(questionnaireId);
        if (null == groups && null == groupLogic) {
            return Collections.emptyList();
        }

        var logicMap = groupLogic.stream().collect(Collectors.groupingBy(AnswerGroupLogic::getAnswerGroupCode));
        var groupDto = groups.stream()
                .map(group -> new AnswerGroupDto(group,logicMap.get(group.getCode())))
                .collect(Collectors.toList());
        return groupDto;
    }

    private ResponseResult<QuestionnaireResponse> getQuestionnaireResponse(Questionnaire questionnaire) {
        var question = new Question();
        question.setQuestionnaireId(questionnaire.getId());
        var questions = questionRepository.findAll(Example.of(question));
        var questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        var answers = answerRepository.findByQuestionIdIn(questionIds);
        var questionnaireResponse = new QuestionnaireResponse(questionnaire, questions, answers);
        return ResponseResult.success("get success", questionnaireResponse);
    }

    private void saveQuestion(List<QuestionRequest> questionRequest, Long questionnaireId) {
        if (null == questionRequest || null == questionnaireId) {
            return;
        }
        questionRequest.forEach(request -> {
            var question = new Question();
            BeanUtils.copyProperties(request, question);
            question.setQuestionnaireId(questionnaireId);
            questionRepository.save(question);
            Long questionId = question.getId();
            var answers = request.getAnswer().stream()
                    .map(answerRequest -> {
                        var answer = new Answer();
                        BeanUtils.copyProperties(answerRequest, answer);
                        answer.setQuestionId(questionId);
                        return answer;
                    }).collect(Collectors.toList());
            answerRepository.saveAll(answers);
        });
    }

    private void saveJudgeLogic(List<JudgeLogicRequest> judgeLogicRequest, Long questionnaireId) {
        if (null == judgeLogicRequest || null == questionnaireId) {
            return;
        }
        //todo 逻辑校验，逻辑不能自相矛盾
        var judgeLogics = judgeLogicRequest.stream()
                .map(request -> {
                    var judgeLogic = new JudgeLogic();
                    BeanUtils.copyProperties(request, judgeLogic);
                    judgeLogic.setQuestionnaireId(questionnaireId);
                    return judgeLogic;
                }).collect(Collectors.toList());
        judgeLogicRepository.saveAll(judgeLogics);

    }

}
