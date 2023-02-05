package com.ssafy.trycatch.common.domain;

public enum TargetType {
    QUESTION("question"), ANSWER("answer"), FEED("feed"), ROADMAP("roadmap"), USER("user"), DEFAULT("default");

    private final String type;

    TargetType(String type) {this.type = type;}

    public String getType() {return type;}

}