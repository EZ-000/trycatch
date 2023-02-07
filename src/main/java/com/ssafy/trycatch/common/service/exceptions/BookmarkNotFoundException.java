package com.ssafy.trycatch.common.service.exceptions;

public class BookmarkNotFoundException extends RuntimeException {

    public BookmarkNotFoundException() {
        super();
    }

    public BookmarkNotFoundException(String message) {
        super(message);
    }
}
