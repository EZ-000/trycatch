package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Banner;
import com.ssafy.trycatch.common.domain.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService extends CrudService<Banner, Long, BannerRepository> {

    @Autowired
    public BannerService(BannerRepository repository) {
        super(repository);
    }
}
