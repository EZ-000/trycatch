package com.ssafy.trycatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/search")
public class SearchController {
    @GetMapping()
    public String search() {
        return "Q&A나 피드 등의 타입 구분하여 원하는 필터로 검색합니다.";
    }
}
