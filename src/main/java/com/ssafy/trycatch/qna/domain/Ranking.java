package com.ssafy.trycatch.qna.domain;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ranking")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 나중에 사용 방법에 따라 ENUM TargetType 등으로 수정해야합니다.
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "category_name")
    private QuestionCategory category;

    @Column(name = "score")
    private Integer score;

}