package com.ssafy.trycatch.qna.service.exceptions;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException() {
        super();
    }

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
