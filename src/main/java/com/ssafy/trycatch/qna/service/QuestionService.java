package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.service.exceptions.QuestionCategoryNotFoundException;
import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESQuestionRepository;

import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import com.ssafy.trycatch.qna.service.exceptions.RequestUserNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public List<Question> findAllQuestionsByCategory(QuestionCategory questionCategory, Pageable pageable) {
        final List<Question> questions = questionRepository
                .findByCategoryNameOrderByCreatedAtDesc(questionCategory, pageable);

        return questions;
    }

    @Transactional
    public void updateQuestion(Long userId, Long questionId, String category, String title, String content, String errorCode, List<String> tags, Boolean hidden) {
        final Question question = questionRepository
                .findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        if (question.getUser().getId() != userId) throw new RequestUserNotValidException();

        final QuestionCategory questionCategory = Optional
                .ofNullable(QuestionCategory.valueOf(category))
                .orElseThrow(QuestionCategoryNotFoundException::new);

        question.setCategoryName(questionCategory);
        question.setTitle(title);
        question.setContent(content);
        question.setErrorCode(errorCode);
        question.setTags(String.join(",", tags));
        question.setHidden(hidden);
        questionRepository.save(question);
    }


    /**
     * @throws IllegalArgumentException questionId가 없는 경우
     */
    public void deleteQuestion(Long questionId, Long userId) {
        final Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        if (question.getUser().getId() != userId) throw new RequestUserNotValidException();
        questionRepository.deleteById(questionId);
    }

	public List<Question> findQuestionListByAnswerId(List<Long> answerIdList) {
        // answerIdList.stream().map(e->questionRepository)
        return Collections.emptyList();
    }
}
