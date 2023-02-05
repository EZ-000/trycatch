package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.qna.domain.Question;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.List;

@Data
public class FindBookmarkedQuestionDto implements Serializable {
    private final Long questionId;
    private final String title;
    private final String content;
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
            List<String> tags,
            Integer viewCount,
            Integer likeCount,
            Integer answerCount,
            Long createdAt
    ) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
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
                .tags(List.of(question.getTags().split(",")))
                .viewCount(question.getViewCount())
                .likeCount(question.getLikes())
                .answerCount(question.getAnswers().size())
                .createdAt(question.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant().toEpochMilli())
                .build();
    }
}
