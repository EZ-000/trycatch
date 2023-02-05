package com.ssafy.trycatch.qna.controller.dto;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.List;

import javax.validation.constraints.Size;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;

import lombok.Builder;
import lombok.Data;

/**
 * A DTO for the {@link com.ssafy.trycatch.qna.domain.Question} entity
 */
@Data
public class FindQuestionResponseDto implements Serializable {
    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     *
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindQuestionResponseDto from(
            Question question, SimpleUserDto simpleUserDto, Boolean isLiked, Boolean isBookmarked
    ) {

        return FindQuestionResponseDto.builder().questionId(question.getId()).author(simpleUserDto).category(
                                              question.getCategoryName()).title(question.getTitle()).content(question.getContent()).errorCode(
                                              question.getErrorCode()).tags(List.of(question.getTags().split(","))).likeCount(
                                              question.getLikes()).viewCount(question.getViewCount()).timestamp(
                                              question.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).updatedAt(
                                              question.getUpdatedAt().toEpochMilli()).isLiked(isLiked).isSolved(question.getChosen())
                                      .isBookmarked(isBookmarked).build();
    }
    private final Long questionId;
    @Size(max = 50)
    private final SimpleUserDto author;
    @Size(max = 30)
    private final QuestionCategory category;
    @Size(max = 50)
    private final String title;
    private final String content;
    private final String errorCode;
    private final List<String> tags;
    private final Integer likeCount;
    private final Integer answerCount;
    private final Integer viewCount;
    private final Long timestamp;
    private final Long updatedAt;
    private final Boolean isLiked;
    private final Boolean isSolved;
    private final Boolean isBookmarked;

    @Builder
    public FindQuestionResponseDto(
            Long questionId, SimpleUserDto author, QuestionCategory category, String title, String content,
            String errorCode, List<String> tags, Integer likeCount, Integer answerCount, Integer viewCount,
            Long timestamp, Long updatedAt, Boolean isLiked, Boolean isSolved, Boolean isBookmarked,
            List<FindAnswerResponseDto> answers
    ) {
        this.questionId = questionId;
        this.author = author;
        this.category = category;
        this.title = title;
        this.content = content;
        this.errorCode = errorCode;
        this.tags = tags;
        this.likeCount = likeCount;
        this.answerCount = answerCount;
        this.viewCount = viewCount;
        this.timestamp = timestamp;
        this.updatedAt = updatedAt;
        this.isLiked = isLiked;
        this.isSolved = isSolved;
        this.isBookmarked = isBookmarked;
    }
}