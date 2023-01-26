package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

public class FindAnswerResponseDto {

    private final Long answerId;

    private final Long questionId;

    private final String username;

    private final String content;

    private final LocalDateTime createdAt;

    private final Boolean chosen;

    private final Integer likes;

    private final Boolean hidden;

    @Builder
    public FindAnswerResponseDto(Long answerId, Long questionId, String username, String content, LocalDateTime createdAt, Boolean chosen, Integer likes, Boolean hidden) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.chosen = chosen;
        this.likes = likes;
        this.hidden = hidden;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param answer 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindAnswerResponseDto from(Answer answer) {

        final Question question = answer.getQuestion();
        final User user = answer.getUser();

        return FindAnswerResponseDto.builder()
                .answerId(answer.getId())
                .questionId(question.getId())
                .username(user.getUsername())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .chosen(answer.getChosen())
                .likes(answer.getLikes())
                .hidden(answer.getHidden())
                .build();
    }
}

