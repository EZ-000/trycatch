package com.ssafy.trycatch.user.domain;

import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.gamification.domain.ChallengeGroup;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "github_node_id", length = 50)
    private String githubNodeId;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 50)
    @Column(name = "git_address", length = 50)
    private String gitAddress;

    @Column(name = "activated")
    private Boolean activated;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 50)
    @Column(name = "calendar_mail", length = 50)
    private String calendarMail;

    @Column(name = "confirmation_code")
    private Integer confirmationCode;

    @NotNull
    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @OneToMany(mappedBy = "follower")
    private Set<Follow> followers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "followee")
    private Set<Follow> followees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Answer> answers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ChallengeGroup> challengeGroups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Read> reads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<MyBadge> myBadges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<History> histories = new LinkedHashSet<>();

}