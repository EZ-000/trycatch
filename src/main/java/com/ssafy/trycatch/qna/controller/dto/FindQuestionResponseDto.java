package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ssafy.trycatch.common.domain.TargetType.ANSWER;
import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

/**
 * A DTO for the {@link com.ssafy.trycatch.qna.domain.Question} entity
 */
@Data
public class FindQuestionResponseDto implements Serializable {

    public static FindQuestionResponseDto from(
            Question question,
            SimpleUserDto simpleUserDto,
            Boolean isLiked,
            Boolean isBookmarked,
            List<FindAnswerResponseDto> answers
    ) {
        return FindQuestionResponseDto.builder()
                .questionId(question.getId())
                .author(simpleUserDto)
                .category(question.getCategoryName())
                .title(question.getTitle())
                .content(question.getContent())
                .errorCode(question.getErrorCode())
                .tags(List.of(question.getTags()
                        .split(",")))
                .likeCount(question.getLikes())
                .answerCount(question.getAnswers().size())
                .viewCount(question.getViewCount())
                .timestamp(question.getCreatedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .updatedAt(question.getUpdatedAt()
                        .toEpochMilli())
                .isLiked(isLiked)
                .isSolved(question.getChosen())
                .isBookmarked(isBookmarked)
                .answers(answers)
                .build();
    }


    /**
     * {@code Question} 엔티티로부터 {@code QuestionResponseDto} 인스턴스를 생성하는 팩토리 메서드
     *
     * @param question 엔티티
     * @return 새로운 DTO 인스턴스
     */
    public static FindQuestionResponseDto from(
            Question question,
            SimpleUserDto simpleUserDto,
            User requestUser,
            Boolean isLiked,
            Boolean isBookmarked,
            LikesService likesService
    ) {
        final Set<Answer> answers = question.getAnswers();
        final List<FindAnswerResponseDto> answerResponseDtoList = new ArrayList<>();

        for (Answer answer : answers) {
            final Boolean isAnswerLiked = likesService
                    .isLikedByUserAndTarget(requestUser.getId(), answer.getId(), ANSWER);

            final FindAnswerResponseDto responseDto = FindAnswerResponseDto
                    .from(answer, requestUser, isAnswerLiked);

            answerResponseDtoList.add(responseDto);
        }

        return FindQuestionResponseDto.builder()
                .questionId(question.getId())
                .author(simpleUserDto)
                .category(question.getCategoryName())
                .title(question.getTitle())
                .content(question.getContent())
                .errorCode(question.getErrorCode())
                .tags(List.of(question.getTags()
                                      .split(",")))
                .likeCount(question.getLikes())
                .answerCount(question.getAnswers().size())
                .viewCount(question.getViewCount())
                .timestamp(question.getCreatedAt()
                                   .atZone(TZ_SEOUL)
                                   .toInstant()
                                   .toEpochMilli())
                .updatedAt(question.getUpdatedAt()
                                   .toEpochMilli())
                .isLiked(isLiked)
                .isSolved(question.getChosen())
                .isBookmarked(isBookmarked)
                .answers(answerResponseDtoList)
                .build();
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
    private final List<FindAnswerResponseDto> answers;

    @Builder
    public FindQuestionResponseDto(
            Long questionId,
            SimpleUserDto author,
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
        this.answers = answers;
    }
}