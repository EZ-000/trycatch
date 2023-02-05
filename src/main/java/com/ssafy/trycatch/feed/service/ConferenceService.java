package com.ssafy.trycatch.feed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.feed.domain.Conference;
import com.ssafy.trycatch.feed.domain.ConferenceRepository;

@Service
public class ConferenceService extends CrudService<Conference, Long, ConferenceRepository> {

    @Autowired
    public ConferenceService(ConferenceRepository repository) {
        super(repository);
    }
}
