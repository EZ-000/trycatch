package com.ssafy.trycatch.elasticsearch.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user-#{@environment.getProperty('elasticsearch.index.prefix')}")
public class ESUser {

    @Id
    private String id;

    @Field(type = FieldType.Long, name = "uid")
    private Long uid;

    @Field(type = FieldType.Keyword, name = "tags")
    private List<String> tags;

    @Field(type = FieldType.Dense_Vector, name = "vector",  dims = 768)
    private List<Float> vector;

    public ESUser(Long uid, Collection<String> tags, List<Float> vector) {
        this.uid = uid;
        this.tags = new ArrayList<>(tags);
        this.vector = vector;
    }
}
