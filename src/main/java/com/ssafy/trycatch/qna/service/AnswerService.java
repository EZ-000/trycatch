package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.AnswerRepository;
import com.ssafy.trycatch.qna.service.exceptions.AnswerNotFoundException;
import com.ssafy.trycatch.qna.service.exceptions.RequestUserNotValidException;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserService userService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserService userService) {
        this.answerRepository = answerRepository;
        this.userService = userService;
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);
    }

    @Transactional
    public void updateAnswer(Long userId, Long answerId, String content, Boolean hidden) {
        final Answer answer = answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);

        if (answer.getUser().getId() != userId)
            throw new RequestUserNotValidException();

        answer.setContent(content);
        answer.setHidden(hidden);
        answerRepository.save(answer);
    }

}
