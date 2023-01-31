package com.ssafy.trycatch.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.ReportRepository;
import com.ssafy.trycatch.user.controller.dto.ReportRequestDto;

@Service
public class ReportService {
	private final ReportRepository reportRepository;

	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	public void registerReason(ReportRequestDto reason) {
		reportRepository.save(reason.newReport());
	}
}
