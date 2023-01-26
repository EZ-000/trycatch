package com.ssafy.trycatch.feed.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(indexName = "blog_posts")
public class ESFeed {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private  String title;

    @Field(type = FieldType.Keyword)
    private List<String> author;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Text)
    private String company_en;

    @Field(type = FieldType.Text)
    private String company_ko;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date)
    private LocalDate create_at;

    @Field(type = FieldType.Text)
    private List<String> images;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @PersistenceCreator
    public ESFeed(String id, String title, List<String> author, String url, String company_en, String company_ko, String content, LocalDate create_at, List<String> images, List<String> tags) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.url = url;
        this.company_en = company_en;
        this.company_ko = company_ko;
        this.content = content;
        this.create_at = create_at;
        this.images = images;
        this.tags = tags;
    }
}
