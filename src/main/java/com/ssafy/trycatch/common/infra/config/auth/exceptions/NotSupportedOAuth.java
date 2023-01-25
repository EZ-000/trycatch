package com.ssafy.trycatch.common.infra.config.auth.exceptions;

public class NotSupportedOAuth extends RuntimeException {

    public NotSupportedOAuth() {
    }

    public NotSupportedOAuth(String message) {
        super(message);
    }
}
