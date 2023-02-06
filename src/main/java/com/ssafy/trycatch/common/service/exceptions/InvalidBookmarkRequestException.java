package com.ssafy.trycatch.common.service.exceptions;

public class InvalidBookmarkRequestException extends RuntimeException {

    public InvalidBookmarkRequestException() {
        super();
    }

    public InvalidBookmarkRequestException(String message) {
        super(message);
    }
}
