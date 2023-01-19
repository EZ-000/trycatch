package com.ssafy.trycatch.qna.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "name", length = 30)
    private String name;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private Set<Ranking> rankings = new LinkedHashSet<>();

}