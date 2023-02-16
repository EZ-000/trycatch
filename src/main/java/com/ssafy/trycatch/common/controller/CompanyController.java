package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.controller.dto.CompanyResponseDto;
import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.common.service.exceptions.CompanyNotFoundException;
import com.ssafy.trycatch.user.controller.dto.UserFeedDto;
import com.ssafy.trycatch.user.domain.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/company")
public class CompanyController {

	private final CompanyService companyService;

	@Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping("/logos")
	public ResponseEntity<List<String>> companyLogos() {
		return ResponseEntity.ok(companyService.findCompanyLogos());
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<CompanyResponseDto> findCompanyProfile(
		@PathVariable Long companyId,
		@AuthUserElseGuest User requestUser) {
		Company company = companyService.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		List<UserFeedDto> feedList = companyService.findFeedList(company,requestUser)
			.stream()
			.sorted(Comparator.comparing(UserFeedDto::getCreatedAt).reversed())
			.collect(Collectors.toList());
		CompanyResponseDto result = CompanyResponseDto.from(company, requestUser, feedList);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/id/{companyName}")
	public ResponseEntity<Long> findCompanyName(
		@PathVariable String companyName) {
		try {
			final Long companyId = companyService.findCompanyIdByCompanyName(companyName);
			return ResponseEntity.ok(companyId);
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.ok(-1L);
		}
	}
}
