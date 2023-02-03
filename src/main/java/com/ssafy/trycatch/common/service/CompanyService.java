package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends CrudService<Company, Long, CompanyRepository> {

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        super(companyRepository);
    }
}
