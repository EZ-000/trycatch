package com.ssafy.trycatch.common.service.exceptions;

public class GuestNotAllowedException extends RuntimeException {

    public GuestNotAllowedException() {
        super();
    }

    public GuestNotAllowedException(String message) {
        super(message);
    }
}
