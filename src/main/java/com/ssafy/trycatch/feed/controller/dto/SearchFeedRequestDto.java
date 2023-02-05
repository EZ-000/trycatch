package com.ssafy.trycatch.feed.controller.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchFeedRequestDto {

    @Nullable
    private String query = null;

    private FeedSortOption sort = FeedSortOption.date;

    private boolean subscribe = false;

    private boolean advanced = false;

    @Nullable
    private LocalDate publishDateStart = null;

    @Nullable
    private LocalDate publishDateEnd = null;

    private Integer page = 0;

    private Integer size = 10;

    @JsonSetter("publishDateStart")
    public void setPublishDateStart(String s) {
        this.publishDateStart = LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
    }

    @JsonSetter("publishDateEnd")
    public void setPublishDateEnd(String s) {
        this.publishDateEnd = LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
    }

    public enum FeedSortOption {
        date("publish_date"), user("vector");

        public final String name;

        FeedSortOption(String name) {
            this.name = name;
        }
    }
}

