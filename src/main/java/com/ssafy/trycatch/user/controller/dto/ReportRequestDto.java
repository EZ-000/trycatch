package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.domain.Report;

public class ReportRequestDto {
	private Long reporter;
	private Long targetId;
	private String targetType;
	private String content;

	public Report newReport() {
		return Report.builder()
			.reporter(reporter)
			.targetId(targetId)
			.targetType(targetType)
			.content(content).build();
	}
}
