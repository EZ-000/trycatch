package com.ssafy.trycatch.qna.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.AnswerRepository;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

@SpringBootTest
class AnswerServiceTest {

    @Autowired
    AnswerService answerService;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Test
    @Order(1)
    @DisplayName("답변 저장")
    void saveAnswer() {

        long countBefore = answerRepository.count();

        // given
        Answer answer = createAnswer(1L);

        // when
        Answer saveAnswer = answerService.saveAnswer(answer);

        // then
        long countAfter = answerRepository.count();
        assertEquals(countBefore + 1, countAfter);

    }

    @Test
    @Order(2)
    @DisplayName("Question Id를 FK로 가지는 답변 모두 조회")
    void findByQuestionId() throws Exception {
        // given

        // when
        long questionId = 1L;

        // then
        boolean result = answerService.findByQuestionId(questionId)
                                      .stream()
                                      .allMatch(answer -> answer.getQuestion().getId().equals(questionId));

        assertTrue(result);
    }

    private Answer createAnswer(Long questionId) {
        Answer answer = new Answer();
        Question question = questionService.findQuestionById(questionId);
        User user = userService.findUserById(1L);
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setContent("test content");
        answer.setChosen(false);
        answer.setLikes(0);
        answer.setHidden(false);
        return answer;
    }
}