package com.ssafy.trycatch.gamification.service.exception;

public class ChallengeNotFoundException extends RuntimeException {

    public ChallengeNotFoundException() {
        super();
    }

    public ChallengeNotFoundException(String message) {
        super(message);
    }
}
