package com.ssafy.trycatch.gamification.domain;

import com.ssafy.trycatch.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "my_challenge")
public class MyChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_id", nullable = false)
    @ToString.Exclude
    private Challenge challenge;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "progress")
    private Long progress;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_info")
    private StatusInfo statusInfo;

    @Column(name = "start_from")
    private LocalDateTime startFrom;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "earned_at")
    private LocalDateTime earnedAt;
}