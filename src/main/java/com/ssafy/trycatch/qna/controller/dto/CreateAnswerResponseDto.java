package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.ZoneId;

@Data
public class CreateAnswerResponseDto implements Serializable {

    private final Long questionId;
    private final String username;
    private final String content;
    private final Long timestamp;
    private final Boolean chosen;
    private final Integer likes;
    private final Boolean hidden;

    @Builder
    public CreateAnswerResponseDto(Long questionId, String username, String content, Long timestamp, Boolean chosen, Integer likes, Boolean hidden) {
        this.questionId = questionId;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
        this.chosen = chosen;
        this.likes = likes;
        this.hidden = hidden;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param answer 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static CreateAnswerResponseDto from(Answer answer) {
        final Question question = answer.getQuestion();
        final User user = answer.getUser();

        return CreateAnswerResponseDto.builder()
                .questionId(question.getId())
                .username(user.getUsername())
                .content(answer.getContent())
                .timestamp(question.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant().toEpochMilli())
                .chosen(answer.getChosen())
                .likes(answer.getLikes())
                .hidden(answer.getHidden())
                .build();
    }
}
