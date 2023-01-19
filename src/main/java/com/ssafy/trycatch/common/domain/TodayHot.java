package com.ssafy.trycatch.common.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "today_hot")
public class TodayHot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 80)
    @Column(name = "title", length = 80)
    private String title;

    @Column(name = "score")
    private Integer score;

}