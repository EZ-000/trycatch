package com.ssafy.trycatch.elasticsearch.domain;

import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "${elasticsearch.question.index}}")
public class ESQuestion {

    public static ESQuestion of(Long questionId, CreateQuestionRequestDto requestDto) {
        return ESQuestion.builder()
                .questionId(questionId)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .tags(requestDto.getTags())
                .build();
    }

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long questionId;

    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text)
    private List<String> tags;

    @Builder
    public ESQuestion(Long questionId, String category, String title, String content, List<String> tags) {
        this.questionId = questionId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
}
