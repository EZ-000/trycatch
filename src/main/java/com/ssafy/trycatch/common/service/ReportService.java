package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Report;
import com.ssafy.trycatch.common.domain.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends CrudService<Report, Long, ReportRepository> {

    @Autowired
    public ReportService(ReportRepository repository) {
        super(repository);
    }
}
