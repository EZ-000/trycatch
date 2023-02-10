package com.ssafy.trycatch.elasticsearch.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
@Document(indexName = "dev_blog")
public class ESFeed {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Keyword, name = "name")
    private String name;

    @Field(type = FieldType.Long, name = "pk")
    private Long pk;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Keyword, name = "authors")
    private List<String> authors;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Integer, name = "image_count")
    private Integer imageCount;

    @Field(type = FieldType.Keyword, name = "tags")
    private List<String> tags;

    @Field(type = FieldType.Keyword, name = "keywords")
    private List<String> keywords;

    @Field(type = FieldType.Date, format = DateFormat.date, name = "publish_date")
    private LocalDate publishDate;

    @Field(type = FieldType.Text, name = "summary")
    private String summary;

    @Field(type = FieldType.Keyword, name = "thumbnail")
    private String thumbnailUrl;

    @Field(type = FieldType.Dense_Vector, name = "vector", dims = 768)
    private List<Float> vector;
}
