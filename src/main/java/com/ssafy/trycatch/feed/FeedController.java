package com.ssafy.trycatch.feed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/feed")
public class FeedController {
    @GetMapping("/list")
    public ResponseEntity<String> findPosts() {
        return ResponseEntity.ok("포스트 리스트를 최신순으로 반환합니다. 토큰이 있을 경우 연관도 순으로 반영합니다.");
    }

    @GetMapping("/bookmark")
    public ResponseEntity<String> findBookmarks() {
        return ResponseEntity.ok("사용자의 즐겨찾기 리스트를 조회합니다.");
    }

    @PostMapping("/bookmark")
    public ResponseEntity<String> createBookmark() {
        return ResponseEntity.ok("원하는 기술 블로그 글을 즐겨찾기할 수 있습니다.");
    }

    @PutMapping("/bookmark")
    public ResponseEntity<String> removeBookmark() {
        return ResponseEntity.ok("즐겨찾기를 취소할 수 있습니다.");
    }

    @PostMapping("/like")
    public ResponseEntity<String> like() {
        return ResponseEntity.ok("좋아요합니다.");
    }

    @PutMapping("/like")
    public ResponseEntity<String> unlike() {
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

    @GetMapping("/search")
    public ResponseEntity<String> search() {
        return ResponseEntity.ok("Q&A나 피드 등의 타입 구분하여 원하는 필터로 검색합니다.");
    }

}
