package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.BookmarkRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService extends CrudService<Bookmark, Long, BookmarkRepository> {

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        super(bookmarkRepository);
    }

    public Bookmark getBookmark(Long userId, Long targetId, TargetType targetType) {
        return repository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                        .orElseGet(Bookmark::new);
    }
}
