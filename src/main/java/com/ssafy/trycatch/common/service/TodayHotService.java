package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.TodayHot;
import com.ssafy.trycatch.common.domain.TodayHotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodayHotService extends CrudService<TodayHot, Long, TodayHotRepository> {

    @Autowired
    public TodayHotService(TodayHotRepository repository) {
        super(repository);
    }
}
