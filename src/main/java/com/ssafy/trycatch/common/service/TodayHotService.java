package com.ssafy.trycatch.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.TodayHot;
import com.ssafy.trycatch.common.domain.TodayHotRepository;

@Service
public class TodayHotService extends CrudService<TodayHot, Long, TodayHotRepository> {

    @Autowired
    public TodayHotService(TodayHotRepository repository) {
        super(repository);
    }
}
