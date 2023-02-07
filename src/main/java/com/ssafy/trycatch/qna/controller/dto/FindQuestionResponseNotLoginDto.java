package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.FindUserInQNANotLoginDto;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

@Data
public class FindQuestionResponseNotLoginDto implements Serializable {
    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     *
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindQuestionResponseNotLoginDto from(
            Question question, List<Answer> answers, CompanyService companyService
    ) {

        final User author = question.getUser();
        final List<FindAnswerResponseNotLoginDto> answerDtos = answers.stream()
                                                                      .map((Answer answer) -> FindAnswerResponseNotLoginDto.from(answer,
                                                                                                                                 companyService))
                                                                      .collect(Collectors.toList());

        return FindQuestionResponseNotLoginDto.builder()
                .questionId(question.getId())
                .author(FindUserInQNANotLoginDto.from(author, companyService))
                .category(question.getCategoryName())
                .title(question.getTitle())
                .content(question.getContent())
                .errorCode(question.getErrorCode())
                .tags(List.of(question.getTags()
                                      .split(",")))
                .likeCount(question.getLikes())
                .answerCount(answerDtos.size())
                .viewCount(question.getViewCount())
                .timestamp(question.getCreatedAt()
                                   .atZone(TZ_SEOUL)
                                   .toInstant()
                                   .toEpochMilli())
                .updatedAt(question.getUpdatedAt()
                                   .toEpochMilli())
                .isLiked(false)
                .isSolved(question.getChosen())
                .isBookmarked(false)
                .answers(answerDtos)
                .build();
    }

    private final Long questionId;
    @Size(max = 50)
    private final FindUserInQNANotLoginDto author;
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
    private final List<FindAnswerResponseNotLoginDto> answers;

    @Builder
    public FindQuestionResponseNotLoginDto(
            Long questionId,
            FindUserInQNANotLoginDto author,
            QuestionCategory category,
            String title,
            String content,
            String errorCode,
            List<String> tags,
            Integer likeCount,
            Integer answerCount,
            Integer viewCount,
            Long timestamp,
            Long updatedAt,
            Boolean isLiked,
            Boolean isSolved,
            Boolean isBookmarked,
            List<FindAnswerResponseNotLoginDto> answers
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
        this.answers = answers;
    }
}
