package com.ssafy.trycatch.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.BookmarkRepository;
import com.ssafy.trycatch.common.domain.TargetType;

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

    public Bookmark getLastBookmark(Long userId, Long targetId, TargetType targetType) {
        List<Bookmark> bookmarkList = repository.streamByUserIdAndTargetIdAndTargetType(userId,
                                                                                        targetId,
                                                                                        targetType);
        final Bookmark lastBookmark;
        if (bookmarkList.size() != 0) {
            lastBookmark = bookmarkList.get(bookmarkList.size() - 1);
        } else {
            lastBookmark = new Bookmark(0L, 0L, 0L, TargetType.DEFAULT, false);
        }
        return lastBookmark;
    }

    public Boolean isBookmarkByUserAndTarget(Long userId, Long targetId, TargetType targetType) {
        return repository.existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(userId, targetId, targetType);
    }

    /**
     * @param userId 유저 아이디
     * @param targetType 타겟 콘텐츠 타입 (QUESTION, FEED, ROADMAP ...)
     * @param activated 활성화 여부
     * @return 타겟 타입에 맞는 활성화된 북마크 인스턴스 리스트 List<Bookmark> 반환
     */
    public List<Bookmark> getActivatedBookmarks(Long userId, TargetType targetType, Boolean activated) {
        return repository
                .streamByUserIdAndTargetTypeAndActivatedOrderByIdDesc(userId, targetType, activated);
    }
}
