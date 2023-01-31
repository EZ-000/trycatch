package com.ssafy.trycatch.common.domain;

import org.springframework.stereotype.Service;

@Service
public class ReportService {
	private final ReportRepository reportRepository;

	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}
}
