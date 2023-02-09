package com.ssafy.trycatch.roadmap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.domain.RoadmapRepository;
import com.ssafy.trycatch.roadmap.service.exceptions.RoadmapNotFoundException;

@Service
public class RoadmapService extends CrudService<Roadmap, Long, RoadmapRepository> {
    @Autowired
    public RoadmapService(RoadmapRepository roadmapRepository) {
        super(roadmapRepository);
    }

    public Roadmap findRoadmap(Long userId) {
        return repository.findByUserId(userId)
                         .orElseThrow(RoadmapNotFoundException::new);
    }

    public void modify(Long userId, Roadmap roadmap) {
        Roadmap saved = repository.findByUserId(userId)
                                  .orElseThrow(RoadmapNotFoundException::new);
        saved.setEdge(roadmap.getEdge());
        saved.setNode(roadmap.getNode());
        saved.setTitle(roadmap.getTitle());
        saved.setTag(roadmap.getTag());
        repository.save(saved);
    }

    public Roadmap findByRoadmapId(Long roadmapId) {
        return repository.findById(roadmapId).orElseThrow(RoadmapNotFoundException::new);
    }

    public List<Roadmap> findTopList() {
        return repository.findTop3ByOrderByLikesDescIdAsc();
    }
}
