package com.ssafy.trycatch.roadmap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://beta.try-catch.duckdns.org",
        "https://i8e108.p.ssafy.io"
}, maxAge = 3600)
@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
    @GetMapping("/list")
    public ResponseEntity<String> findRoadmaps() {
        return ResponseEntity.ok("로드맵 정보를 조회합니다.");
    }
}
