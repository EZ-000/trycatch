package com.ssafy.trycatch.gamification.domain;

import com.ssafy.trycatch.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "my_badge")
public class MyBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "badge_id", nullable = false)
    @ToString.Exclude
    private Badge badge;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_info")
    private StatusInfo statusInfo;

    @Column(name = "on_profile")
    private Boolean onProfile;

    @NotNull
    @Column(name = "earned_at", nullable = false)
    private Instant earnedAt;

}