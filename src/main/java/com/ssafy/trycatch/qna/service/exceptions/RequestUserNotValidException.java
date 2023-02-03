package com.ssafy.trycatch.qna.service.exceptions;

public class RequestUserNotValidException extends RuntimeException {

    public RequestUserNotValidException() {
        super();
    }

    public RequestUserNotValidException(String message) {
        super(message);
    }
}
