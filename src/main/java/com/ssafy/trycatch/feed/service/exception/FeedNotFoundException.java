package com.ssafy.trycatch.feed.service.exception;

public class FeedNotFoundException extends RuntimeException {

    public FeedNotFoundException() {
        super();
    }

    public FeedNotFoundException(String message) {
        super(message);
    }
}
