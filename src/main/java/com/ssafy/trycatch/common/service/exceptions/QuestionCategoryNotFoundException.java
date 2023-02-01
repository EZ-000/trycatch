package com.ssafy.trycatch.common.service.exceptions;

public class QuestionCategoryNotFoundException extends RuntimeException {

    public QuestionCategoryNotFoundException() {
        super();
    }

    public QuestionCategoryNotFoundException(String message) {
        super(message);
    }
}
