package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESQuestionRepository;
import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ESQuestionRepository esQuestionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ESQuestionRepository esQuestionRepository) {
        this.questionRepository = questionRepository;
        this.esQuestionRepository = esQuestionRepository;
    }

    public Question saveQuestion(Question question) {
        final Question entity = questionRepository.save(question);
        esQuestionRepository.save(ESQuestion.of(entity));
        return entity;
    }

    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> findQuestionsByTitle(String title, Pageable pageable) {
        return questionRepository.findByTitleLike(title, pageable);
    }

    public List<Question> findAllQuestions(Pageable pageable) {
        final Page<Question> questionPage = questionRepository.findAll(pageable);
        return questionPage.stream().collect(Collectors.toList());
    }

    public Question putQuestion(PutQuestionRequestDto putDto) {
        final Question before = questionRepository.findById(putDto.getId())
                .orElseThrow(QuestionNotFoundException::new);
        final Question after = putDto.updateTo(before);
        // key 값이 같다는 전제 하에 49 ~ 51이 필요없고 JPA가 있으면 업데이트를 해줌 없으면 insert
        return questionRepository.save(after);
    }

    /**
     * @throws IllegalArgumentException questionId가 없는 경우
     */
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

	public List<Question> findQuestionListByAnswerId(List<Long> answerIdList) {
        // answerIdList.stream().map(e->questionRepository)
        return Collections.emptyList();
    }
}
