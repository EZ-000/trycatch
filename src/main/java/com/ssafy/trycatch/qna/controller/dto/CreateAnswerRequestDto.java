package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateAnswerRequestDto {
    private String content;

    public Answer newAnswer(Question question, User user) {
        return Answer.builder().question(question).user(user).content(content).createdAt(LocalDateTime.now()).updatedAt(Instant.now()).chosen(false).likes(0).hidden(false).build();
    }
}
