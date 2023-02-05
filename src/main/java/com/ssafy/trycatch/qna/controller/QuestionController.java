package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.qna.controller.dto.*;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.FollowService;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.trycatch.common.domain.TargetType.QUESTION;


@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final CompanyService companyService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    private final FollowService followService;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            AnswerService answerService,
            UserService userService,
            CompanyService companyService,
            LikesService likesService,
            BookmarkService bookmarkService,
            FollowService followService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
        this.companyService = companyService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
        this.followService = followService;
    }

    @GetMapping
    public ResponseEntity<List<?>> findAllQuestions(
            @PageableDefault Pageable pageable,
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestParam String category
    ) {
        final QuestionCategory enumCategory = QuestionCategory.of(category);
        final List<Question> questions = questionService.findAllQuestionsByCategory(enumCategory, pageable);
        final List<FindQuestionResponseDto> responseDtoList = new ArrayList<>();

        for (Question question : questions) {

            long targetId = question.getId();

            boolean isLiked = likesService.isLikedByUserAndTarget(userId, targetId, QUESTION);
            boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(userId, targetId, QUESTION);

            User requestUser = userService.findUserById(userId);
            User author = question.getUser();

            final SimpleUserDto userInQNADto = SimpleUserDto.from(requestUser, author);
            final FindQuestionResponseDto responseDto = FindQuestionResponseDto.from(
                    question, userInQNADto, isLiked, isBookmarked
            );

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
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {

        if (!createQuestionRequestDto.getAuthorId().equals(userId)) {
            throw new IllegalArgumentException("JWT Token user id not equals author id");
        }

        final Question savedEntity = questionService.saveQuestion(createQuestionRequestDto);

        final long targetId = savedEntity.getId();
        final boolean isLiked = likesService.isLikedByUserAndTarget(userId, targetId, QUESTION);
        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(userId, targetId, QUESTION);
        final User user = savedEntity.getUser();

        final SimpleUserDto userInQNADto = SimpleUserDto.from(user, user);
        final CreateQuestionResponseDto responseDto
                = CreateQuestionResponseDto.from(savedEntity, userInQNADto, isLiked, isBookmarked);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * PutQuestionRequestDto로 받은 수정 요청을 처리하고,
     * CreateQuestionResponseDto로 반환
     */
    @PutMapping("/{questionId}")
    public ResponseEntity putQuestion(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        questionService.updateQuestion(
                userId,
                putQuestionRequestDto.getQuestionId(),
                putQuestionRequestDto.getCategory(),
                putQuestionRequestDto.getTitle(),
                putQuestionRequestDto.getContent(),
                putQuestionRequestDto.getErrorCode(),
                putQuestionRequestDto.getTags(),
                putQuestionRequestDto.getHidden()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(
            @Nullable @AuthenticationPrincipal Long userId,
            @PathVariable Long questionId
    ) {
        questionService.deleteQuestion(questionId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code Question#id}로 {@code Question}을 찾아 {@code QuestionResponseDto}로 반환하여 반환
     *
     * @param questionId {@code Question}의 id
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<?> findQuestionById(
            @PathVariable("questionId") Long questionId,
            @Nullable @AuthenticationPrincipal Long userId
    ) {
        final Question question = questionService.findQuestionById(questionId);
        final User author = question.getUser();

        FindQuestionResponseDto responseDto;
        if (null == userId) {
            final SimpleUserDto simpleUserDto = SimpleUserDto.from(author);
            responseDto = FindQuestionResponseDto.from(question, simpleUserDto, false, false);
        } else {
            final User requestUser = userService.findUserById(userId);
            final SimpleUserDto simpleUserDto = SimpleUserDto.from(author, requestUser);
            final long authorId = author.getId();
            final boolean isLiked = likesService.isLikedByUserAndTarget(userId, authorId, QUESTION);
            final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(userId, authorId, QUESTION);
            responseDto = FindQuestionResponseDto.from(question, simpleUserDto, isLiked, isBookmarked);
        }

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{questionId}/answer")
    public ResponseEntity<CreateAnswerResponseDto> createAnswers(
            @PathVariable Long questionId,
            @RequestBody CreateAnswerRequestDto createAnswerRequestDto,
            @Nullable @AuthenticationPrincipal Long userId
    ) {
        // 생성
        final Question question = questionService.findQuestionById(questionId);
        final User user = userService.findUserById(userId);
        final Answer newAnswer = createAnswerRequestDto.newAnswer(question, user);
        final Answer savedAnswer = answerService.saveAnswer(newAnswer);
        // 응답
        final List<Answer> answers = answerService.findByQuestionId(question.getId());
        final TargetType type = QUESTION;
        final Boolean isLiked = Optional.ofNullable(likesService.getLikes(user.getId(), question.getId(), type).getActivated())
                .orElse(false);
        final Boolean isBookmarked = Optional.ofNullable(bookmarkService.getBookmark(user.getId(), question.getId(), type).getActivated())
                .orElse(false);
        return ResponseEntity.ok(CreateAnswerResponseDto.from(question, answers, user, companyService, isLiked, isBookmarked));
    }


    @PutMapping("/{questionId}/answer")
    public ResponseEntity putAnswer(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PutAnswerRequestDto putAnswerRequestDto
    ) {
        answerService.updateAnswer(
                userId,
                putAnswerRequestDto.getAnswerId(),
                putAnswerRequestDto.getContent(),
                putAnswerRequestDto.getHidden()
        );
        return ResponseEntity.ok().build();
    }

    // MOCK API: 질문 검색
    @GetMapping("/search")
    public ResponseEntity<List<SearchQuestionResponseDto>> search(
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<AcceptAnswerResponseDto> acceptAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @Nullable @AuthenticationPrincipal Long userId
    ) {
        // 채택
        final Question question = questionService.acceptAnswer(questionId, answerId);
        final List<Answer> answers = new ArrayList<>(question.getAnswers());
        final User author = question.getUser();
        AcceptAnswerResponseDto responseDto;
        if (null == userId) {
            final List<FindAnswerResponseDto> answerDtoList = answers.stream()
                     .map(FindAnswerResponseDto::from)
                     .collect(Collectors.toList());
            final SimpleUserDto authorDto = SimpleUserDto.from(author);
            responseDto = AcceptAnswerResponseDto.from(question, answerDtoList, authorDto, false, false);
        } else {
            final User requestUser = userService.findUserById(userId);

            final List<FindAnswerResponseDto> answerDtoList = answers.stream()
                    .map(ans -> FindAnswerResponseDto.from(ans, requestUser))
                    .collect(Collectors.toList());

            final SimpleUserDto authorDto = SimpleUserDto.from(author, requestUser);

            final boolean isLiked = likesService.isLikedByUserAndTarget(userId, author.getId(), QUESTION);
            final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(userId, author.getId(), QUESTION);

            responseDto = AcceptAnswerResponseDto.from(question, answerDtoList, authorDto, isLiked, isBookmarked);
        }

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
