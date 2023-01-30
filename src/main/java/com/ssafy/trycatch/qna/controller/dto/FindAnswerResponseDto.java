package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.FindUserInQNADto;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FindAnswerResponseDto implements Serializable {
    private final Long answerId;
    private final FindUserInQNADto author;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer likecount;
    private final Boolean isLiked;


    @Builder
    public FindAnswerResponseDto(Long answerId, FindUserInQNADto author, String content, LocalDateTime createdAt, Integer likecount, Boolean isLiked) {
        this.answerId = answerId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.likecount = likecount;
        this.isLiked = isLiked;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param answer 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindAnswerResponseDto from(Answer answer, User user) {

        final Question question = answer.getQuestion();
        final User author = answer.getUser();

        return FindAnswerResponseDto.builder()
                .answerId(answer.getId())
                .author(FindUserInQNADto.from(user, author))
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .likecount(answer.getLikes())
                .isLiked(false)
                .build();
    }
}

