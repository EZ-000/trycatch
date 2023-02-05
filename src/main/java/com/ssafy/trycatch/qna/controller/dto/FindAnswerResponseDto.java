package com.ssafy.trycatch.qna.controller.dto;

import java.io.Serializable;
import java.time.ZoneId;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;
import com.ssafy.trycatch.user.domain.User;

import lombok.Builder;
import lombok.Data;

@Data
public class FindAnswerResponseDto implements Serializable {
    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     *
     * @param answer 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindAnswerResponseDto from(Answer answer, User user) {

        final Question question = answer.getQuestion();
        final User author = answer.getUser();

        return FindAnswerResponseDto.builder()
                .answerId(answer.getId())
                .author(SimpleUserDto.builder()
                                .author(author)
                                .requestUser(user)
                                .build())
                .content(answer.getContent())
                .timestamp(question.getCreatedAt()
                                   .atZone(ZoneId.of("Asia/Seoul"))
                                   .toInstant()
                                   .toEpochMilli())
                .updatedAt(answer.getUpdatedAt()
                                 .toEpochMilli())
                .likeCount(answer.getLikes())
                .isLiked(false)
                .accepted(answer.getChosen())
                .build();
    }

    public static FindAnswerResponseDto from(Answer answer) {
        final Question question = answer.getQuestion();
        final User author = answer.getUser();
        final long timestamp = question.getCreatedAt()
                                       .atZone(ZoneId.of("Asia/Seoul"))
                                       .toInstant()
                                       .toEpochMilli();

        return FindAnswerResponseDto.builder()
                .answerId(answer.getId())
                .author(SimpleUserDto.builder()
                                .author(author)
                                .build())
                .content(answer.getContent())
                .timestamp(timestamp)
                .updatedAt(answer.getUpdatedAt()
                                 .toEpochMilli())
                .likeCount(answer.getLikes())
                .isLiked(false)
                .accepted(answer.getChosen())
                .build();
    }

    private final Long answerId;
    private final SimpleUserDto author;
    private final String content;
    private final Long timestamp;
    private final Long updatedAt;
    private final Integer likeCount;
    private final Boolean isLiked;
    private final Boolean accepted;

    @Builder
    public FindAnswerResponseDto(
            Long answerId,
            SimpleUserDto author,
            String content,
            Long timestamp,
            Long updatedAt,
            Integer likeCount,
            Boolean isLiked,
            Boolean accepted
    ) {
        this.answerId = answerId;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.updatedAt = updatedAt;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.accepted = accepted;
    }
}

