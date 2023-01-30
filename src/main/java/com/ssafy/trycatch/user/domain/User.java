package com.ssafy.trycatch.user.domain;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ssafy.trycatch.feed.domain.Company;
import org.hibernate.annotations.DynamicInsert;

import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.gamification.domain.ChallengeGroup;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;

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
@DynamicInsert
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
	@Column(name = "company_id", nullable = false)
	private Long companyId;

	@NotNull
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@NotNull
	@Column(name = "points", nullable = false)
	private Integer points;

	@OneToMany(mappedBy = "follower")
	@ToString.Exclude
	private Set<Follow> followers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "followee")
	@ToString.Exclude
	private Set<Follow> followees = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<Answer> answers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<ChallengeGroup> challengeGroups = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<Subscription> subscriptions = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<Question> questions = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<Read> reads = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<MyBadge> myBadges = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private Set<History> histories = new LinkedHashSet<>();

	@Size(max = 200)
	@Column(name = "introduction", length = 200)
	private String introduction;

	@Builder
	public User(String email, String name,String url, String nodeId){
		this.email = email;
		this.username = name;
		this.gitAddress = url;
		this.githubNodeId = nodeId;
	}
}