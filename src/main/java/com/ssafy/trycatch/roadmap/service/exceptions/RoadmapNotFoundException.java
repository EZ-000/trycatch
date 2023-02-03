package com.ssafy.trycatch.roadmap.service.exceptions;

public class RoadmapNotFoundException extends RuntimeException{
	public RoadmapNotFoundException() {
		super();
	}

	public RoadmapNotFoundException(String message) {
		super(message);
	}
}
