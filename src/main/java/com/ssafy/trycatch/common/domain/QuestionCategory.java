package com.ssafy.trycatch.common.domain;

public enum QuestionCategory {
    DEV("dev"),
    CAREER("career"),
    DEFAULT("default");

    private final String category;

    QuestionCategory(String category) { this.category = category; }

    public String getCategory() { return category; }

    public static QuestionCategory of(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return DEFAULT;
        }
    }
}
