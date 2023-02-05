package com.ssafy.trycatch.elasticsearch.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "question")
public class ESQuestion {

    public static ESQuestion of(CreateQuestionRequestDto requestDto) {
        return ESQuestion.builder().title(requestDto.getTitle()).content(requestDto.getContent()).category(
                requestDto.getCategory()).tags(requestDto.getTags()).build();
    }

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text)
    private List<String> tags;

    @Builder
    public ESQuestion(String category, String title, String content, List<String> tags) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
}
