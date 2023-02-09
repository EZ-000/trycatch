package com.ssafy.trycatch.feed.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFeedRequestDto {

    @Builder.Default
    private String query = null;

    @Builder.Default
    private FeedSortOption sort = FeedSortOption.date;

    @Builder.Default
    private boolean subscribe = false;

    @Builder.Default
    private boolean advanced = false;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDateStart = null;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDateEnd = null;

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
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

