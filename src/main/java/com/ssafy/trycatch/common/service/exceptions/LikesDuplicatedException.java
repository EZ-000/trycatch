package com.ssafy.trycatch.common.service.exceptions;

public class LikesDuplicatedException extends RuntimeException {

    public LikesDuplicatedException() {
        super();
    }

    public LikesDuplicatedException(String message) {
        super(message);
    }
}
