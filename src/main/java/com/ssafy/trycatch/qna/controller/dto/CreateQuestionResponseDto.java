package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.FindUserInQNADto;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A DTO for the {@link Question} entity
 */
@Data
public class CreateQuestionResponseDto implements Serializable {
    private final Long questionId;
    @Size(max = 50)
    private final FindUserInQNADto author;
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
    private final Boolean isLiked;
    private final Boolean isSolved;
    private final Boolean isBookmarked;
    private final List<FindAnswerResponseDto> answers;

    @Builder
    public CreateQuestionResponseDto(Long questionId, FindUserInQNADto author, QuestionCategory category, String title, String content, String errorCode, List<String> tags, Integer likeCount, Integer answerCount, Integer viewCount, Long timestamp, Boolean isLiked, Boolean isSolved, Boolean isBookmarked, List<FindAnswerResponseDto> answers) {
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
        this.isLiked = isLiked;
        this.isSolved = isSolved;
        this.isBookmarked = isBookmarked;
        this.answers = answers;
    }

    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static CreateQuestionResponseDto from(
            Question question,
            CompanyService companyService,
            Boolean isLiked,
            Boolean isBookmarked
    ) {
        final User user = question.getUser();
        final User author = question.getUser();
        final List<String> temptags = new ArrayList<>(Arrays.asList("42good", "1stprizeisours"));

        return CreateQuestionResponseDto.builder()
                .questionId(question.getId())
                .author(FindUserInQNADto.from(user, author, companyService))
                .category(question.getCategoryName())
                .title(question.getTitle())
                .content(question.getContent())
                .errorCode(question.getErrorCode())
                .tags(temptags)
                .likeCount(0)
                .answerCount(0)
                .viewCount(question.getViewCount())
                .timestamp(question.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant().toEpochMilli())
                .isLiked(isLiked)
                .isSolved(question.getChosen())
                .isBookmarked(isBookmarked)
                .answers(new ArrayList<>())
                .build();
    }
}