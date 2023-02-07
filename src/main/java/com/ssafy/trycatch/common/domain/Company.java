package com.ssafy.trycatch.common.domain;

import com.ssafy.trycatch.feed.domain.Conference;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.user.domain.Subscription;
import com.ssafy.trycatch.user.domain.User;
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
    @Builder.Default
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @Builder.Default
    private Set<Conference> conferences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @Builder.Default
    private Set<Feed> feeds = new LinkedHashSet<>();

    @Size(max = 50)
    @Column(name = "name_ko", length = 50)
    private String nameKo;

    @Size(max = 50)
    @Column(name = "name_en", length = 50)
    private String nameEn;

    @Size(max = 50)
    @Column(name = "group_name", length = 50)
    private String groupName;

    @Size(max = 50)
    @Column(name = "platform", length = 50)
    private String platform;

    @Size(max = 255)
    @Column(name = "icon")
    private String icon;

    @Size(max = 255)
    @Column(name = "logo")
    private String logo;

    @Size(max = 255)
    @Column(name = "blog")
    private String blog;

    @Size(max = 255)
    @Column(name = "rss")
    private String rss;

    @Size(max = 10)
    @Column(name = "rss_type", length = 10)
    private String rssType;

    @OneToMany(mappedBy = "company")
    @Builder.Default
    private Set<User> users = new LinkedHashSet<>();

}