package com.ssafy.trycatch;

import org.springframework.web.bind.annotation.GetMapping;

public class SearchController {
    @GetMapping("/search")
    public String search() {
        return "Q&A나 피드 등의 타입 구분하여 원하는 필터로 검색합니다.";
    }
}
