package com.ssafy.trycatch.elasticsearch.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

    @Field(type = FieldType.Keyword)
    private String companyEn;

    @Field(type = FieldType.Keyword)
    private String companyKo;

    @Field(type = FieldType.Text)
    private String content;

    @Field(name = "created_at", type = FieldType.Date)
    private String createdAt;

    @Field(type = FieldType.Text)
    private List<String> images;

    @Field(type = FieldType.Keyword)
    private List<String> tags;
}
