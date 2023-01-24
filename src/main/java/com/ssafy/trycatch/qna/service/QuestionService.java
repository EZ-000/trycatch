package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.qna.controller.dto.CreateQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Question> findQuestions() { return (List<Question>) questionRepository.findAll(); }

    public Question putQuestion(Long questionId, String title, String content, Boolean hidden) {
        Question question;
        question = questionRepository.findById(questionId).get();
        question.setTitle(title);
        question.setContent(content);
        question.setHidden(hidden);

        return question;
    }

    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
