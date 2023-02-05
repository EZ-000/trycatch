package com.ssafy.trycatch.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.Report;
import com.ssafy.trycatch.common.domain.ReportRepository;

@Service
public class ReportService extends CrudService<Report, Long, ReportRepository> {

    @Autowired
    public ReportService(ReportRepository repository) {
        super(repository);
    }
}
