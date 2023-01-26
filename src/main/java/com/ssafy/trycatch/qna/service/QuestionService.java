package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
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
        return questionRepository.save(after);
    }

    /**
     *
     * @param questionId
     * @throws IllegalArgumentException questionId가 없는 경우
     */
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
