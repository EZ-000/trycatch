package com.ssafy.trycatch.feed.service.exception;

public class ESFeedNotFoundException extends RuntimeException {

    public ESFeedNotFoundException() {
        super();
    }

    public ESFeedNotFoundException(String message) {
        super(message);
    }
}
