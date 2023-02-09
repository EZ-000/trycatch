package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.CompanyRepository;
import com.ssafy.trycatch.common.service.exceptions.CompanyNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService extends CrudService<Company, Long, CompanyRepository> {

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        super(companyRepository);
    }


    public List<String> findCompanyLogos() {
        return repository.findAllByLogoIsNotNull()
                .stream()
                .map(Company::getLogo)
                .collect(Collectors.toList());
    }

    public Long findCompanyIdByCompanyName(Long companyName) {
        return repository.findCompanyIdByCompanyName(companyName)
            .orElseThrow(CompanyNotFoundException::new);
    }
}
