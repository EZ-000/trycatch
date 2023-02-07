package com.ssafy.trycatch.qna.controller;

import static com.ssafy.trycatch.common.domain.TargetType.ANSWER;
import static com.ssafy.trycatch.common.domain.TargetType.QUESTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.qna.controller.dto.AcceptAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.CreateAnswerRequestDto;
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
                    isLiked,
                    isBookmarked);

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
            @AuthUserElseGuest User requestUser, @RequestBody CreateQuestionRequestDto createQuestionRequestDto
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

        return ResponseEntity.status(201).body(responseDto);
    }

    /**
     * @param requestUser 로그인된 유저
     * @param putQuestionRequestDto 질문 수정 dto
     * @return 수정 성공 시 201 Created 응답
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<Void> putQuestion(
            @AuthUserElseGuest User requestUser, @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        questionService.updateQuestion(requestUser.getId(),
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
            @AuthUserElseGuest User requestUser, @PathVariable Long questionId
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
            @PathVariable("questionId") Long questionId, @AuthUserElseGuest User requestUser
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
            final boolean answerIsLiked = likesService
                    .isLikedByUserAndTarget(requestUser.getId(), answerId, ANSWER);

            final FindAnswerResponseDto responseDto = FindAnswerResponseDto.from(
                    answer, requestUser, answerIsLiked);

            answerResponseDtoList.add(responseDto);
        }

        final FindQuestionResponseDto responseDto = FindQuestionResponseDto.from(
                question,
                simpleUserDto,
                isLiked,
                isBookmarked,
                answerResponseDtoList);

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
            @AuthUserElseGuest User requestUser
    ) {
        // 생성
        final Question question = questionService.findQuestionById(questionId);
        final Answer answerDto = createAnswerRequestDto.newAnswer(question, requestUser);
        final Answer answer = answerService.saveAnswer(answerDto);

        // 응답
        final FindAnswerResponseDto answerResponseDto = FindAnswerResponseDto.from(answer);

        return ResponseEntity.status(201).body(answerResponseDto);

    }

    /**
     * @param requestUser 로그인된 유저
     * @param putAnswerRequestDto 답변 수정 dto
     * @return 수정 성공 시 201 Created 응답
     */
    @PutMapping("/{questionId}/answer")
    public ResponseEntity<Void> putAnswer(
            @AuthUserElseGuest User requestUser,
            @RequestBody @Valid PutAnswerRequestDto putAnswerRequestDto
    ) {
        answerService.updateAnswer(requestUser.getId(),
                                   putAnswerRequestDto.getAnswerId(),
                                   putAnswerRequestDto.getContent(),
                                   putAnswerRequestDto.getHidden());
        return ResponseEntity.status(201)
                             .build();
    }

    // MOCK API: 질문 검색
    @GetMapping("/search")
    public ResponseEntity<List<SearchQuestionResponseDto>> search() {
        return ResponseEntity.ok()
                             .build();
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
            final boolean answerIsLiked = likesService
                    .isLikedByUserAndTarget(requestUser.getId(), targetId, ANSWER);

            final FindAnswerResponseDto responseDto = FindAnswerResponseDto.from(
                    answer, requestUser, answerIsLiked);

            answerResponseDtoList.add(responseDto);
        }

        final SimpleUserDto authorDto = SimpleUserDto.builder()
                .author(author)
                .requestUser(requestUser)
                .build();
        final boolean isLiked = likesService.isLikedByUserAndTarget(requestUser.getId(),
                                                                    author.getId(),
                                                                    QUESTION);
        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(requestUser.getId(),
                                                                               author.getId(),
                                                                               QUESTION);
        final AcceptAnswerResponseDto responseDto = AcceptAnswerResponseDto.from(question,
                                                                                 answerResponseDtoList,
                                                                                 authorDto,
                                                                                 isLiked,
                                                                                 isBookmarked);

        return ResponseEntity.status(201).body(responseDto);
    }

    // MOCK API: 에러코드 기반 질문 추천
    @GetMapping("/ec")
    public ResponseEntity<List<SuggestQuestionResponseDto>> suggestQuestions() {
        return ResponseEntity.ok()
                             .build();
    }
}
