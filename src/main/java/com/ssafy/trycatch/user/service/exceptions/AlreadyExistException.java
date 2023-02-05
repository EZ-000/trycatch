package com.ssafy.trycatch.user.service.exceptions;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
