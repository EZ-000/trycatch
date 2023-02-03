package com.ssafy.trycatch.gamification.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    @Size(max = 100)
    @Column(name = "`condition`", length = 100)
    private String condition;

    @Size(max = 30)
    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "start_from")
    private LocalDate startFrom;

    @Column(name = "end_at")
    private LocalDate endAt;

    @OneToMany(mappedBy = "challenge")
    @ToString.Exclude
    private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

}