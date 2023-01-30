package com.ssafy.trycatch.roadmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trycatch.roadmap.controller.dto.RoadmapRequestDto;
import com.ssafy.trycatch.roadmap.controller.dto.RoadmapResponseDto;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;

@RestController
@RequestMapping("/${apiPrefix}/roadmap")
public class RoadmapController {
	private final RoadmapService roadmapService;

	@Autowired
	public RoadmapController(RoadmapService roadmapService) {
		this.roadmapService = roadmapService;
	}

	@GetMapping("/list/{userId}")
	public ResponseEntity<RoadmapResponseDto> findRoadmap(@PathVariable Long userId) {
		final Roadmap roadmap = roadmapService.findRoadmap(userId);
		return ResponseEntity.ok(RoadmapResponseDto.from(roadmap));
	}

	@PostMapping("/list")
	public ResponseEntity<RoadmapResponseDto> registerRoadmap(@RequestBody RoadmapRequestDto roadmapRequestDto) {
		final Roadmap roadmap = roadmapRequestDto.newRoadmap();
		final Roadmap savedRoadmap = roadmapService.register(roadmap);
		return ResponseEntity.ok(RoadmapResponseDto.from(savedRoadmap));
	}

	@PutMapping("/list/{userId}")
	public ResponseEntity<String> modifyRoadmap(@PathVariable Long userId,
		@RequestBody RoadmapRequestDto roadmapRequestDto) {
		final Roadmap roadmap = roadmapRequestDto.newRoadmap();
		roadmapService.modify(userId, roadmap);
		return ResponseEntity.ok().build();
	}
}
