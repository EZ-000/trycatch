package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public Bookmark getBookmark(Long userId, Long targetId, String targetType) {
        return bookmarkRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
    }
}
