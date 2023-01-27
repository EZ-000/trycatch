package com.ssafy.trycatch.elasticsearch.domain;

import com.ssafy.trycatch.qna.domain.Question;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "question")
public class ESQuestion {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Builder
    public ESQuestion(String category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public static ESQuestion of(Question entity) {
        return ESQuestion.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().getName())
                .build();
    }
}
