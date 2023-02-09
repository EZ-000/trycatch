package com.ssafy.trycatch.roadmap.controller;

import static com.ssafy.trycatch.common.domain.TargetType.*;

import java.util.ArrayList;
import java.util.List;

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
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapListResponseDto;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapRequestDto;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapResponseDto;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
    private final RoadmapService roadmapService;
    private final UserService userService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @Autowired
    public RoadmapController(
            RoadmapService roadmapService,
            UserService userService,
            LikesService likesService,
            BookmarkService bookmarkService
    ) {
        this.roadmapService = roadmapService;
        this.userService = userService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoadmapListResponseDto>> findAllRoadmap(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        List<Roadmap> allRoadmaps = roadmapService.findAll();
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
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
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
            @RequestBody RoadmapRequestDto roadmapRequestDto, @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        Roadmap roadmap = roadmapRequestDto.toEntity(requestUser);
        Roadmap registeredRoadmap = roadmapService.register(roadmap);
        RoadmapResponseDto result = RoadmapResponseDto.from(registeredRoadmap, false, false);

        return ResponseEntity.ok(result);
    }

    @SuppressWarnings("UnusedDeclaration")
    @PutMapping("/{userName}")
    public ResponseEntity<String> modifyRoadmap(
            @PathVariable String userName,
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody RoadmapRequestDto roadmapRequestDto
    ) {
        final Roadmap roadmap = roadmapRequestDto.toEntity(requestUser);
        roadmapService.modify(requestUser.getId(), roadmap);
        return ResponseEntity.status(201)
                             .build();
    }

    @SuppressWarnings("UnusedDeclaration")
    @DeleteMapping("/{userName}")
    public ResponseEntity<String> removeRoadmap(
            @PathVariable String userName, @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        Long userId = requestUser.getId();
        Roadmap roadmap = roadmapService.findRoadmap(userId);
        roadmapService.removeById(roadmap.getId());
        return ResponseEntity.status(204)
                             .build();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<RoadmapListResponseDto>> findPopularRoadmap(
        @ApiParam(hidden = true) @AuthUserElseGuest User requestUser
    ) {
        List<Roadmap> allRoadmaps = roadmapService.findTopList();
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
}
