package com.ssafy.trycatch.common.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    List<Bookmark> streamByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    boolean existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(
            Long userId, Long targetId,
            TargetType targetType
    );

    /**
     * @param userId 유저 아이디
     * @param targetType 타겟 타입 (QUESTION, FEED, ROADMAP ...)
     * @param activated 활성화 여부
     * @return 북마크 인스턴스 중 타겟 타입에 해당하고 활성화된 상태인 List<Bookmark> 반환
     */
    List<Bookmark> streamByUserIdAndTargetTypeAndActivatedOrderByIdDesc(
            Long userId, TargetType targetType, Boolean activated);

}
