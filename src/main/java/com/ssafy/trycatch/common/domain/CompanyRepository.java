package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByLogoIsNotNull();

    Optional<Long> findCompanyIdByName(String companyName);
}