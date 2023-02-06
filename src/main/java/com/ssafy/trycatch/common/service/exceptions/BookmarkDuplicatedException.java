package com.ssafy.trycatch.common.service.exceptions;

public class BookmarkDuplicatedException extends RuntimeException {

    public BookmarkDuplicatedException() {
        super();
    }

    public BookmarkDuplicatedException(String message) {
        super(message);
    }
}
