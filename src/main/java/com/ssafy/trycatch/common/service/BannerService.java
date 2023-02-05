package com.ssafy.trycatch.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.Banner;
import com.ssafy.trycatch.common.domain.BannerRepository;

@Service
public class BannerService extends CrudService<Banner, Long, BannerRepository> {

    @Autowired
    public BannerService(BannerRepository repository) {
        super(repository);
    }
}
