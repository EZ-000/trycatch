package com.ssafy.trycatch.common.service.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super();
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
