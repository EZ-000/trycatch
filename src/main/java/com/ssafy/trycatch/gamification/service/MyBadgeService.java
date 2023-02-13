package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import com.ssafy.trycatch.gamification.domain.MyBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.trycatch.gamification.domain.StatusInfo.SUCCESS;

@Service
public class MyBadgeService extends CrudService<MyBadge, Long, MyBadgeRepository> {

    @Autowired
    public MyBadgeService(MyBadgeRepository repository) {
        super(repository);
    }
    public List<MyBadge> findByUserIdAndSuccess(Long userId) { return repository
            .findByUser_IdAndStatusInfo(userId, SUCCESS); }
}
