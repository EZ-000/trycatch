package com.ssafy.trycatch.gamification.domain;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Builder.Default
    private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

}