package com.ssafy.trycatch.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class ArticleController {
    @GetMapping("/bookmark")
    public String readBookmark() {
        return "사용자의 즐겨찾기 리스트를 조회합니다.";
    }

    @PostMapping("/bookmark")
    public String createBookmark() {
        return "원하는 기술 블로그 글을 즐겨찾기할 수 있습니다.";
    }

    @PutMapping("/bookmark")
    public String deleteBookmark() {
        return "즐겨찾기를 취소할 수 있습니다.";
    }

    @PutMapping("/user/ck")
    public String commonKnowledge() {
        return "개발 상식 글을 피드에서 완전히 제외합니다.";
    }

    @GetMapping("feed/list")
    public String readFeed() {
        return "피드 목록을 최신순으로 반환합니다. 토큰이 있을 경우 연관도 순으로 반영합니다.";
    }
}
