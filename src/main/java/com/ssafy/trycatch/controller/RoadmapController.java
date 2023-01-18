package com.ssafy. trycatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
    @GetMapping("/list")
    public String readRoadmap() {
        return "로드맵 정보를 조회합니다.";
    }
}
