package com.ssafy.trycatch.qna.controller;

import static com.ssafy.trycatch.common.domain.TargetType.QUESTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.qna.controller.dto.AcceptAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.CreateAnswerRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PutAnswerRequestDto;
import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.SearchQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.SuggestQuestionResponseDto;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;
import com.ssafy.trycatch.user.domain.User;

@SuppressWarnings("DuplicatedCode")
@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            AnswerService answerService,
            LikesService likesService,
            BookmarkService bookmarkService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    public ResponseEntity<List<FindQuestionResponseDto>> findAllQuestions(
            @AuthUserElseGuest User requestUser,
            @RequestParam String category,
            @PageableDefault Pageable pageable
    ) {
        final QuestionCategory enumCategory = QuestionCategory.of(category);
        final List<Question> questions = questionService.findAllQuestionsByCategory(enumCategory, pageable);
        final List<FindQuestionResponseDto> responseDtoList = new ArrayList<>();

        for (Question question : questions) {

            final long targetId = question.getId();
            final boolean isLiked
                    = likesService.isLikedByUserAndTarget(requestUser.getId(), targetId, QUESTION);

            final boolean isBookmarked
                    = bookmarkService.isBookmarkByUserAndTarget(requestUser.getId(), targetId, QUESTION);

            final User author = question.getUser();
            final SimpleUserDto userInQNADto = SimpleUserDto.builder()
                    .author(author)
                    .requestUser(requestUser)
                    .build();

            final FindQuestionResponseDto responseDto
                    = FindQuestionResponseDto.from(question, userInQNADto, isLiked, isBookmarked);

            responseDtoList.add(responseDto);
        }

        return ResponseEntity.ok(responseDtoList);
    }

    /**
     * {@code Question} 엔티티를 생성 후 데이터베이스에 저장
     *
     * @param createQuestionRequestDto 질문 생성 요청 DTO
     */
    @PostMapping
    public ResponseEntity<CreateQuestionResponseDto> createQuestion(
            @AuthUserElseGuest User requestUser,
            @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {

        if (!createQuestionRequestDto.getAuthorId().equals(requestUser.getId())) {
            throw new IllegalArgumentException("JWT Token user id not equals author id");
        }

        final Question savedEntity = questionService.saveQuestion(createQuestionRequestDto);

        final long targetId = savedEntity.getId();

        final boolean isLiked = likesService.isLikedByUserAndTarget(requestUser.getId(), targetId, QUESTION);

        final boolean isBookmarked
                = bookmarkService.isBookmarkByUserAndTarget(requestUser.getId(), targetId, QUESTION);

        final User user = savedEntity.getUser();

        final SimpleUserDto userInQNADto = SimpleUserDto.builder()
                .author(user)
                .requestUser(requestUser)
                .build();

        final CreateQuestionResponseDto responseDto
                = CreateQuestionResponseDto.from(savedEntity, userInQNADto, isLiked, isBookmarked);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * PutQuestionRequestDto로 받은 수정 요청을 처리하고,
     * CreateQuestionResponseDto로 반환
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<Void> putQuestion(
            @AuthUserElseGuest User requestUser,
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        questionService.updateQuestion(
                requestUser.getId(), putQuestionRequestDto.getQuestionId(),
                putQuestionRequestDto.getCategory(), putQuestionRequestDto.getTitle(),
                putQuestionRequestDto.getContent(), putQuestionRequestDto.getErrorCode(),
                putQuestionRequestDto.getTags(), putQuestionRequestDto.getHidden()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @AuthUserElseGuest User requestUser,
            @PathVariable Long questionId
    ) {
        questionService.deleteQuestion(questionId, requestUser.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * {@code Question#id}로 {@code Question}을 찾아 {@code QuestionResponseDto}로 반환하여 반환
     *
     * @param questionId {@code Question}의 id
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<FindQuestionResponseDto> findQuestionById(
            @PathVariable("questionId") Long questionId,
            @AuthUserElseGuest User requestUser
    ) {
        final Question question = questionService.findQuestionById(questionId);
        final User author = question.getUser();

        final SimpleUserDto simpleUserDto = SimpleUserDto.builder()
                .author(author)
                .requestUser(requestUser)
                .build();

        final long authorId = author.getId();

        final boolean isLiked = likesService.isLikedByUserAndTarget(requestUser.getId(), authorId, QUESTION);

        final boolean isBookmarked
                = bookmarkService.isBookmarkByUserAndTarget(requestUser.getId(), authorId, QUESTION);

        final FindQuestionResponseDto responseDto
                = FindQuestionResponseDto.from(question, simpleUserDto, isLiked, isBookmarked);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{questionId}/answer")
    public ResponseEntity<CreateAnswerResponseDto> createAnswers(
            @PathVariable Long questionId,
            @RequestBody CreateAnswerRequestDto createAnswerRequestDto,
            @AuthUserElseGuest User requestUser
    ) {
        // 생성
        final Question question = questionService.findQuestionById(questionId);
        createAnswerRequestDto.newAnswer(question, requestUser);
        // 응답
        final List<Answer> answers = answerService.findByQuestionId(question.getId());
        final TargetType type = QUESTION;
        final Boolean isLiked
                = Optional.ofNullable(likesService.getLikes(requestUser.getId(), question.getId(), type)
                                                  .getActivated())
                          .orElse(false);

        final Boolean isBookmarked
                = Optional.ofNullable(bookmarkService.getBookmark(requestUser.getId(), question.getId(), type)
                                                     .getActivated())
                          .orElse(false);
        return ResponseEntity.ok(
                CreateAnswerResponseDto.from(question, answers, requestUser, isLiked, isBookmarked));
    }

    @PutMapping("/{questionId}/answer")
    public ResponseEntity<Void> putAnswer(
            @AuthUserElseGuest User requestUser,
            @RequestBody @Valid PutAnswerRequestDto putAnswerRequestDto,
            @PathVariable String questionId
    ) {
        answerService.updateAnswer(
                requestUser.getId(),
                putAnswerRequestDto.getAnswerId(),
                putAnswerRequestDto.getContent(),
                putAnswerRequestDto.getHidden()
        );
        return ResponseEntity.ok().build();
    }

    // MOCK API: 질문 검색
    @GetMapping("/search")
    public ResponseEntity<List<SearchQuestionResponseDto>> search(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<AcceptAnswerResponseDto> acceptAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @AuthUserElseGuest User requestUser
    ) {
        // 채택
        final Question question = questionService.acceptAnswer(questionId, answerId);
        final List<Answer> answers = new ArrayList<>(question.getAnswers());
        final User author = question.getUser();
        final List<FindAnswerResponseDto> answerDtoList = answers.stream().map(
                ans -> FindAnswerResponseDto.from(ans, requestUser)).collect(Collectors.toList());

        final SimpleUserDto authorDto = SimpleUserDto.builder().author(author).requestUser(requestUser).build();
        final boolean isLiked = likesService.isLikedByUserAndTarget(requestUser.getId(), author.getId(),
                                                                    QUESTION
        );
        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(requestUser.getId(),
                                                                               author.getId(), QUESTION
        );
        final AcceptAnswerResponseDto responseDto = AcceptAnswerResponseDto.from(question, answerDtoList,
                                                                                 authorDto, isLiked,
                                                                                 isBookmarked
        );

        return ResponseEntity.ok(responseDto);
    }

    // MOCK API: 에러코드 기반 질문 추천
    @GetMapping("/ec")
    public ResponseEntity<List<SuggestQuestionResponseDto>> suggestQuestions(
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok().build();
    }
}
