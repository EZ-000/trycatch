package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import com.ssafy.trycatch.gamification.domain.MyBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyBadgeService extends CrudService<MyBadge, Long, MyBadgeRepository> {

    @Autowired
    public MyBadgeService(MyBadgeRepository repository) {
        super(repository);
    }
}
