package com.ssafy.trycatch.gamification.domain;

public enum StatusInfo {
    BEFORE("before"), ONGOING("ongoing"), SUCCESS("success"), FAIL("fail"), DEFAULT("default");

    public static StatusInfo of (String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return DEFAULT;
        }
    }

    private final String state;

    StatusInfo(String state) { this.state = state; }

    public String getState() { return state; }
}
