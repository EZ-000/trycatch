package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadService extends CrudService<Read, Long, ReadRepository> {

    @Autowired
    public ReadService(ReadRepository repository) {
        super(repository);
    }
}
