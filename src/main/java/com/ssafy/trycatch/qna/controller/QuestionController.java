package com.ssafy.trycatch.qna.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.common.notification.NotificationService;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;
import com.ssafy.trycatch.qna.controller.dto.AcceptAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.CreateAnswerRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PopularQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PopularTagsResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PutAnswerRequestDto;
import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.SearchQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.SuggestQuestionResponseDto;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.GithubRepo;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.GithubRepoService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.controller.dto.SimpleUserDto;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.GithubService;
import io.swagger.annotations.ApiParam;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import static com.ssafy.trycatch.common.domain.TargetType.ANSWER;
import static com.ssafy.trycatch.common.domain.TargetType.QUESTION;

@Slf4j
@SuppressWarnings("DuplicatedCode")
@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;
    private final NotificationService notificationService;
    private final GithubRepoService githubRepoService;
    private final GithubService githubService;
    private final TokenService tokenService;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            AnswerService answerService,
            LikesService likesService,
            BookmarkService bookmarkService,
            NotificationService notificationService,
            GithubRepoService githubRepoService, GithubService githubService, TokenService tokenService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
        this.notificationService = notificationService;
        this.githubRepoService = githubRepoService;
        this.githubService = githubService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<FindQuestionResponseDto>> findAllQuestions(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestParam String category,
            @PageableDefault Pageable pageable
    ) {
        final QuestionCategory enumCategory = QuestionCategory.of(category);
        final List<Question> questions = questionService.findAllQuestionsByCategory(enumCategory, pageable);
        final List<FindQuestionResponseDto> responseDtoList = new ArrayList<>();

        for (Question question : questions) {

            final long targetId = question.getId();
            final boolean isLiked = likesService.isLikedByUserAndTarget(
                    requestUser.getId(),
                    targetId,
                    QUESTION);

            final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                    requestUser.getId(),
                    targetId,
                    QUESTION);

            final User author = question.getUser();
            final SimpleUserDto userInQNADto = SimpleUserDto.builder()
                    .author(author)
                    .requestUser(requestUser)
                    .build();

            final FindQuestionResponseDto responseDto = FindQuestionResponseDto.from(
                    question,
                    userInQNADto,
                    requestUser,
                    isLiked,
                    isBookmarked,
                    likesService);

            responseDtoList.add(responseDto);
        }

        return ResponseEntity.ok(responseDtoList);
    }

    /**
     * {@code Question} 엔티티를 생성 후 데이터베이스에 저장
     * @param createQuestionRequestDto 질문 생성 요청 DTO
     * @return 생성 성공 시 201 Created 응답
     */
    @PostMapping
    public ResponseEntity<CreateQuestionResponseDto> createQuestion(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser, @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {

        final Question savedEntity = questionService.saveQuestion(requestUser, createQuestionRequestDto);

        final long targetId = savedEntity.getId();

        final boolean isLiked = likesService.isLikedByUserAndTarget(requestUser.getId(), targetId, QUESTION);

        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                requestUser.getId(),
                targetId,
                QUESTION);

        final User user = savedEntity.getUser();

        final SimpleUserDto userInQNADto = SimpleUserDto.builder()
                .author(user)
                .requestUser(requestUser)
                .build();

        final CreateQuestionResponseDto responseDto = CreateQuestionResponseDto.from(
                savedEntity,
                userInQNADto,
                isLiked,
                isBookmarked);

        return ResponseEntity.status(201)
                             .body(responseDto);
    }

    /**
     * @param requestUser 로그인된 유저
     * @param putQuestionRequestDto 질문 수정 dto
     * @return 수정 성공 시 201 Created 응답
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<Void> putQuestion(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        questionService.updateQuestion(
                requestUser.getId(),
                putQuestionRequestDto.getQuestionId(),
                putQuestionRequestDto.getCategory(),
                putQuestionRequestDto.getTitle(),
                putQuestionRequestDto.getContent(),
                putQuestionRequestDto.getErrorCode(),
                putQuestionRequestDto.getTags(),
                putQuestionRequestDto.getHidden());
        return ResponseEntity.status(201)
                             .build();
    }

    /**
     * @param requestUser 로그인된 유저
     * @param questionId 질문 id
     * @return 삭제 성공 시 204 No Content 응답
     */
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser, @PathVariable Long questionId
    ) {
        questionService.deleteQuestion(questionId, requestUser.getId());
        return ResponseEntity.status(204)
                             .build();
    }

    /**
     * {@code Question#id}로 {@code Question}을 찾아 {@code QuestionResponseDto}로 반환하여 반환
     * @param questionId {@code Question}의 id
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<FindQuestionResponseDto> findQuestionById(
            @PathVariable("questionId") Long questionId,
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        final Question question = questionService.findQuestionByIdWithViewCount(questionId);
        final User author = question.getUser();

        final SimpleUserDto simpleUserDto = SimpleUserDto.builder()
                .author(author)
                .requestUser(requestUser)
                .build();

        final long targetId = question.getId();

        final boolean isLiked = likesService.isLikedByUserAndTarget(
                requestUser.getId(),
                targetId,
                QUESTION);

        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                requestUser.getId(),
                targetId,
                QUESTION);

        final Set<Answer> answers = question.getAnswers();
        final List<FindAnswerResponseDto> answerResponseDtoList = new ArrayList<>();
        for (Answer answer : answers) {
            final long answerId = answer.getId();
            final long userId = requestUser.getId();

            final boolean answerIsLiked = likesService
                    .isLikedByUserAndTarget(requestUser.getId(), answerId, ANSWER);

//            final boolean repoChecked = githubRepoService
//                    .isRepoChecked(userId);

            final GithubRepo githubRepo = githubRepoService
                    .findByUser(userId);

            final FindAnswerResponseDto responseDto = FindAnswerResponseDto.from(
                    answer,
                    requestUser,
                    answerIsLiked,
//                    repoChecked,
                    githubRepo);

            answerResponseDtoList.add(responseDto);
        }

        final FindQuestionResponseDto responseDto = FindQuestionResponseDto.from(
                question,
                simpleUserDto,
                requestUser,
                isLiked,
                isBookmarked,
                likesService);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * @param questionId 질문 id
     * @param createAnswerRequestDto 답변 생성 요청 dto
     * @param requestUser 로그인된 유저
     * @return 생성 성공 시 201 Created 응답
     */
    @PostMapping("/{questionId}/answer")
    public ResponseEntity<FindAnswerResponseDto> createAnswers(
            @PathVariable Long questionId,
            @RequestBody CreateAnswerRequestDto createAnswerRequestDto,
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        // 생성
        final Question question = questionService.findQuestionById(questionId);
        final Answer answerDto = createAnswerRequestDto.newAnswer(question, requestUser);
        final Answer answer = answerService.saveAnswer(answerDto);

        // 응답
        final Long userId = requestUser.getId();

//        final Boolean repoChecked = githubRepoService.isRepoChecked(userId);
        final GithubRepo githubRepo = githubRepoService.findByUser(userId);
        final FindAnswerResponseDto answerResponseDto = FindAnswerResponseDto.from(
                        answer,
//                        repoChecked,
                        githubRepo);

        // 내글에 내가 답변을 생성하는 경우는, 알림을 생성하지 않는다.
        if(!requestUser.getId().equals(question.getUser().getId())) {
            notificationService.notifyAddAnswer(question);
        }

        return ResponseEntity.status(201)
                             .body(answerResponseDto);

    }

    /**
     * @param requestUser 로그인된 유저
     * @param putAnswerRequestDto 답변 수정 dto
     * @return 수정 성공 시 201 Created 응답
     */
    @PutMapping("/{questionId}/answer")
    public ResponseEntity<Void> putAnswer(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody @Valid PutAnswerRequestDto putAnswerRequestDto
    ) {
        answerService.updateAnswer(
                requestUser.getId(),
                putAnswerRequestDto.getAnswerId(),
                putAnswerRequestDto.getContent(),
                putAnswerRequestDto.getHidden());
        return ResponseEntity.status(201)
                             .build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchQuestionResponseDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam String category,
            @PageableDefault Pageable pageable
    ) {
        final QuestionCategory enumCategory = QuestionCategory.of(category);
        final Page<ESQuestion> esQuestions = questionService.search(query, enumCategory, pageable);
        final List<SearchQuestionResponseDto> questions = esQuestions.stream()
                                                                     .map(ESQuestion::getQuestionId)
                                                                     .map(questionService::findQuestionById)
                                                                     .map(SearchQuestionResponseDto::from)
                                                                     .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }

    /**
     * @param questionId 질문 id
     * @param answerId 답변 id
     * @param requestUser 로그인된 유저
     * @return 답변 채택 성공 시 201 Created 응답
     */
    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<AcceptAnswerResponseDto> acceptAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @AuthUserElseGuest User requestUser
    ) {
        // 채택
        final Question question = questionService.acceptAnswer(questionId, answerId, requestUser);
        final User author = question.getUser();
        final Set<Answer> answers = question.getAnswers();
        final List<FindAnswerResponseDto> answerResponseDtoList = new ArrayList<>();
        for (Answer answer : answers) {
            final long targetId = answer.getId();
            final long userId = requestUser.getId();

            final boolean answerIsLiked = likesService
                    .isLikedByUserAndTarget(userId, targetId, ANSWER);

//            final boolean repoChecked = githubRepoService
//                    .isRepoChecked(userId);

            final GithubRepo githubRepo = githubRepoService
                    .findByUser(userId);

            final FindAnswerResponseDto responseDto = FindAnswerResponseDto.from(
                    answer,
                    requestUser,
                    answerIsLiked,
//                    repoChecked,
                    githubRepo);

            answerResponseDtoList.add(responseDto);
        }

        final SimpleUserDto authorDto = SimpleUserDto.builder()
                .author(author)
                .requestUser(requestUser)
                .build();
        final boolean isLiked = likesService.isLikedByUserAndTarget(
                requestUser.getId(),
                author.getId(),
                QUESTION);
        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                requestUser.getId(),
                author.getId(),
                QUESTION);
        final AcceptAnswerResponseDto responseDto = AcceptAnswerResponseDto.from(
                question,
                answerResponseDtoList,
                authorDto,
                isLiked,
                isBookmarked);

        notificationService.notifyAcceptAnswer(question, answerId);

        return ResponseEntity.status(201)
                             .body(responseDto);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PopularQuestionResponseDto>> popularQuestions(
            @AuthUserElseGuest User requestUser,
            @RequestParam @Nullable Optional<String> category,
            @PageableDefault Pageable pageable
    ) {
        final List<Question> questions = questionService.findPopularQuestions(category, pageable);

        final List<PopularQuestionResponseDto> responseDtoList = new ArrayList<>();
        for (Question question : questions) {
            final User author = question.getUser();
            final SimpleUserDto userInQNADto = SimpleUserDto.builder()
                    .author(author)
                    .requestUser(requestUser)
                    .build();

            final Long targetId = question.getId();
            final boolean isLiked = likesService.isLikedByUserAndTarget(
                    requestUser.getId(),
                    targetId,
                    QUESTION);

            final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                    requestUser.getId(),
                    targetId,
                    QUESTION);

            responseDtoList.add(PopularQuestionResponseDto.from(question, userInQNADto, isLiked, isBookmarked));
        }
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/popular-tag")
    public ResponseEntity<PopularTagsResponseDto> popularTags() {
        final List<String> tags = new ArrayList<>(Arrays
                .asList("Spring", "SpringBoot", "React", "TypeScript", "Nginx", "Kafka", "Java", "Python", "PyTorch", "PostgreSQL"));

        return ResponseEntity.ok(PopularTagsResponseDto.from(tags));
    }

    @SneakyThrows
    @PostMapping("/{questionId}/{answerId}/commit")
    public ResponseEntity<Void> commitAnswer(
            @AuthUserElseGuest User requestUser,
            @RequestHeader("Authorization") String token,
            @PathVariable Long questionId,
            @PathVariable Long answerId
    ) {
        final String githubToken = tokenService.getAccessToken(token);

        final GithubRepo githubRepo = githubRepoService.findByUser(requestUser.getId());
        final String repoName = githubRepo.getRepoName();

        final Question question = questionService.findQuestionById(questionId);
        final Answer answer = answerService.findById(answerId);

        final String fileName = "RE: " + question.getTitle() + " " + answer.getCreatedAt().toString();
        final String content = answer.getContent();

        githubService.createFile(githubToken, repoName, fileName, content);

        return ResponseEntity.status(201).build();
    }


    // MOCK API: 에러코드 기반 질문 추천
    @GetMapping("/ec")
    public ResponseEntity<List<SuggestQuestionResponseDto>> suggestQuestions() {
        return ResponseEntity.ok().build();
    }
}
