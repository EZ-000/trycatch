package com.ssafy.trycatch.common.service.exceptions;

public class LikesNotFoundException extends RuntimeException {

    public LikesNotFoundException() {
        super();
    }

    public LikesNotFoundException(String message) {
        super(message);
    }
}
