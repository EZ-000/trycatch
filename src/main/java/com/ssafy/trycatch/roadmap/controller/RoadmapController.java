package com.ssafy.trycatch.roadmap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
    @GetMapping("/list")
    public ResponseEntity<String> findRoadmaps() {
        return ResponseEntity.ok("로드맵 정보를 조회합니다.");
    }
}
