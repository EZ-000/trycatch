package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Category;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

/**
 * A DTO for the {@link Question} entity
 */
@Data
public class CreateQuestionResponseDto implements Serializable {
    @Size(max = 30)
    private final String categoryName;
    @Size(max = 50)
    private final String authorUsername;
    @Size(max = 50)
    private final String title;
    private final String content;
    private final Long timestamp;
    private final Instant updatedAt;
    private final Integer viewCount;
    private final Integer likes;
    private final Boolean hidden;

    @Builder
    public CreateQuestionResponseDto(String categoryName, String authorUsername, String title, String content, Instant updatedAt, Integer viewCount, Integer likes, Boolean hidden, Set<Long> answerIds, Long timestamp) {
        this.categoryName = categoryName;
        this.authorUsername = authorUsername;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
        this.likes = likes;
        this.hidden = hidden;
        this.timestamp = timestamp;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static CreateQuestionResponseDto from(Question question) {

        final Category category = question.getCategory();
        final User author = question.getUser();

        return CreateQuestionResponseDto.builder()
                .categoryName(category.getName())
                .authorUsername(author.getUsername())
                .title(question.getTitle())
                .content(question.getTitle())
                .timestamp(question.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant().toEpochMilli())
                .updatedAt(question.getUpdatedAt())
                .viewCount(question.getViewCount())
                .likes(question.getLikes())
                .hidden(question.getHidden())
                .build();
    }
}