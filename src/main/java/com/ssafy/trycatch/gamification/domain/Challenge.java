package com.ssafy.trycatch.gamification.domain;

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
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "title", length = 30)
    private String title;

    @Size(max = 200)
    @Column(name = "content", length = 30)
    private String content;

    @Column(name = "term")
    private Integer term;

    @Size(max = 500)
    @Column(name="img_src")
    private String imgSrc;

    @OneToMany(mappedBy = "challenge")
    @ToString.Exclude
    @Builder.Default
    private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

}