package com.ssafy.trycatch.user.service.exceptions;

public class TypeNotFoundException extends RuntimeException {

    public TypeNotFoundException() {
        super();
    }

    public TypeNotFoundException(String message) {
        super(message);
    }
}
