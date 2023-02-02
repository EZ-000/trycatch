package com.ssafy.trycatch.roadmap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.domain.RoadmapRepository;
import com.ssafy.trycatch.roadmap.service.exceptions.RoadmapNotFoundException;

@Service
public class RoadmapService {
	private final RoadmapRepository roadmapRepository;

	@Autowired
	public RoadmapService(RoadmapRepository roadmapRepository) {
		this.roadmapRepository = roadmapRepository;
	}

	public Roadmap findRoadmap(Long userId) {
		return roadmapRepository.findByUserId(userId).orElseThrow(RoadmapNotFoundException::new);
	}

	public Roadmap register(Roadmap roadmap) {

		return roadmapRepository.save(roadmap);
	}

	public void modify(Long userId, Roadmap roadmap) {
		Roadmap saved = roadmapRepository.findByUserId(userId).orElseThrow(RoadmapNotFoundException::new);
		saved.setEdge(roadmap.getEdge());
		saved.setNode(roadmap.getNode());
		saved.setTitle(roadmap.getTitle());
		saved.setTag(roadmap.getTag());
		roadmapRepository.save(saved);
	}

	public List<Roadmap> findAll() {
		return roadmapRepository.findAll();
	}

	public void remove(Long userId) {
		final Roadmap savedRoadmap = roadmapRepository.findByUserId(userId).orElseThrow();
		roadmapRepository.delete(savedRoadmap);
	}

	public Long findId(Long userId) {
		return roadmapRepository.findByUserId(userId).orElseThrow().getId();
	}

}
