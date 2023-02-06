package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.qna.domain.Question;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

@Data
public class FindBookmarkedQuestionDto implements Serializable {
    private final Long questionId;
    private final String title;
    private final String content;
    private final String category;
    private final List<String> tags;
    private final Integer viewCount;
    private final Integer likeCount;
    private final Integer answerCount;
    private final Long createdAt;

    @Builder
    public FindBookmarkedQuestionDto(
            Long questionId,
            String title,
            String content,
            String category, List<String> tags,
            Integer viewCount,
            Integer likeCount,
            Integer answerCount,
            Long createdAt
    ) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.answerCount = answerCount;
        this.createdAt = createdAt;
    }

    public static FindBookmarkedQuestionDto from(
            Question question
    ) {
        return FindBookmarkedQuestionDto.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategoryName().toString())
                .tags(List.of(question.getTags().split(",")))
                .viewCount(question.getViewCount())
                .likeCount(question.getLikes())
                .answerCount(question.getAnswers().size())
                .createdAt(question.getCreatedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant().toEpochMilli())
                .build();
    }
}
