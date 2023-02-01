package com.ssafy.trycatch.common.domain;

public enum QuestionCategory {
    DEV("dev"),
    CAREER("career"),
    DEFAULT("default");

    private String category;

    QuestionCategory(String category) { this.category = category; }

    public String getCategory() { return category; }
}
