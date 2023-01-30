package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.FindUserInQNADto;
import com.ssafy.trycatch.user.controller.dto.FindUserInQNANotLoginDto;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.ZoneId;

@Data
public class FindAnswerResponseNotLoginDto implements Serializable {
    private final Long answerId;
    private final FindUserInQNANotLoginDto author;
    private final String content;
    private final Long timestamp;
    private final Integer likecount;
    private final Boolean isLiked;


    @Builder
    public FindAnswerResponseNotLoginDto(Long answerId, FindUserInQNANotLoginDto author, String content, Long timestamp, Integer likecount, Boolean isLiked) {
        this.answerId = answerId;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.likecount = likecount;
        this.isLiked = isLiked;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param answer 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindAnswerResponseNotLoginDto from(Answer answer, CompanyService companyService) {

        final Question question = answer.getQuestion();
        final User author = answer.getUser();

        return FindAnswerResponseNotLoginDto.builder()
                .answerId(answer.getId())
                .author(FindUserInQNANotLoginDto.from(author, companyService))
                .content(answer.getContent())
                .timestamp(question.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant().toEpochMilli())
                .likecount(answer.getLikes())
                .isLiked(false)
                .build();
    }
}
