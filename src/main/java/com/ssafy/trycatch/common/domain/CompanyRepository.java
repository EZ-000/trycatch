package com.ssafy.trycatch.common.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByLogoIsNotNull();

    Optional<Company> findByNameEn(String nameEn);

    @Query(value = "SELECT  b.company_id, COUNT(*) AS cnt\n"
        + "FROM    `read` a, feed b, company c\n"
        + "WHERE   a.feed_id = b.id\n"
        + "    AND b.company_id = c.id\n"
        + "    AND a.read_at BETWEEN DATE_ADD(NOW(),INTERVAL -1 WEEK ) AND NOW()\n"
        + "GROUP BY company_id\n"
        + "ORDER BY cnt DESC, company_id ASC\n"
        + "LIMIT 5", nativeQuery = true)
    List<Long> findTop5PopularCompany();
}