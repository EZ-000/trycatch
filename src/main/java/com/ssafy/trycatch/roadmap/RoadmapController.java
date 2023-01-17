package com.ssafy.trycatch.roadmap;

import org.springframework.web.bind.annotation.GetMapping;

public class RoadmapController {
    @GetMapping("/roadmap/list")
    public String readRoadmap() {
        return "로드맵 정보를 조회합니다.";
    }
}
