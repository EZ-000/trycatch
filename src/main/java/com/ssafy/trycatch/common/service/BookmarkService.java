package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.BookmarkRepository;
import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.List;

@Service
public class BookmarkService extends CrudService<Bookmark, Long, BookmarkRepository> {

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        super(bookmarkRepository);
    }

    public Bookmark getBookmark(Long userId, Long targetId, TargetType targetType) {
        return repository
                .findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                .orElseGet(Bookmark::new);
    }

    public Bookmark getLastBookmark(Long userId, Long targetId, TargetType targetType) {
        List<Bookmark> bookmarkList = repository.streamByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
        final Bookmark lastBookmark;
        if (bookmarkList.size() != 0) {
            lastBookmark = bookmarkList.get(bookmarkList.size() - 1);
        }
        else {
            lastBookmark = new Bookmark(0L, 0L, 0L, TargetType.DEFAULT, false);
        }
        return lastBookmark;
    }
}
