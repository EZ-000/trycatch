package com.ssafy.trycatch.roadmap.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapListResponseDto;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapRequestDto;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapResponseDto;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

import static com.ssafy.trycatch.common.domain.TargetType.QUESTION;
import static com.ssafy.trycatch.common.domain.TargetType.ROADMAP;

@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
    private final RoadmapService roadmapService;
    private final UserService userService;
    private final TokenService tokenService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @Autowired
    public RoadmapController(
            RoadmapService roadmapService, UserService userService, TokenService tokenService,
            LikesService likesService, BookmarkService bookmarkService) {
        this.roadmapService = roadmapService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoadmapListResponseDto>> findAllRoadmap(
            @AuthUserElseGuest User requestUser
    ) {
        List<Roadmap> allRoadmaps = roadmapService.findAll();
//        List<RoadmapListResponseDto> allDtoList = allRoadmaps.stream()
//                                                             .map(RoadmapListResponseDto::from)
//                                                             .collect(Collectors.toList());

        final List<RoadmapListResponseDto> allDtoList = new ArrayList<>();
        for (Roadmap roadmap : allRoadmaps) {
            final long roadmapId = roadmap.getId();

            final boolean isLiked = likesService.isLikedByUserAndTarget(
                    requestUser.getId(),
                    roadmapId,
                    ROADMAP);

            final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                    requestUser.getId(),
                    roadmapId,
                    ROADMAP);

            final RoadmapListResponseDto responseDto = RoadmapListResponseDto.from(
                    roadmap,
                    isBookmarked,
                    isLiked);

            allDtoList.add(responseDto);
        }

        return ResponseEntity.ok(allDtoList);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<RoadmapResponseDto> findRoadmap(
            @AuthUserElseGuest User requestUser,
            @PathVariable String userName
    ) {
        final Long userId = userService.findNameToId(userName);
        final Roadmap roadmap = roadmapService.findRoadmap(userId);

        final long roadmapId = roadmap.getId();

        final boolean isLiked = likesService.isLikedByUserAndTarget(
                requestUser.getId(),
                roadmapId,
                ROADMAP);

        final boolean isBookmarked = bookmarkService.isBookmarkByUserAndTarget(
                requestUser.getId(),
                roadmapId,
                ROADMAP);

        return ResponseEntity.ok(RoadmapResponseDto.from(roadmap, isBookmarked, isLiked));
    }

    @PostMapping("")
    public ResponseEntity<RoadmapResponseDto> registerRoadmap(
            @RequestBody RoadmapRequestDto roadmapRequestDto, @AuthUserElseGuest User requestUser
    ) {
        Roadmap roadmap = roadmapRequestDto.toEntity(requestUser);
        Roadmap registeredRoadmap = roadmapService.register(roadmap);
        RoadmapResponseDto result = RoadmapResponseDto.from(registeredRoadmap, false, false);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<String> modifyRoadmap(
            @PathVariable String userName,
            @AuthUserElseGuest User requestUser,
            @RequestBody RoadmapRequestDto roadmapRequestDto
    ) {
        final Roadmap roadmap = roadmapRequestDto.toEntity(requestUser);
        roadmapService.modify(requestUser.getId(), roadmap);
        return ResponseEntity.ok()
                             .build();
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<String> removeRoadmap(
            @PathVariable String userName, @AuthUserElseGuest User requestUser
    ) {
        roadmapService.removeById(requestUser.getId());
        return ResponseEntity.ok()
                             .build();
    }
}
