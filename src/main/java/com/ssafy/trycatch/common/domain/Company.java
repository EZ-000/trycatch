package com.ssafy.trycatch.common.domain;

import com.ssafy.trycatch.feed.domain.Conference;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.user.domain.Subscription;
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
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private Set<Conference> conferences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private Set<Feed> feeds = new LinkedHashSet<>();

}