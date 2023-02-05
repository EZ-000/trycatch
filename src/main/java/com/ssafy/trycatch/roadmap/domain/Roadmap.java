package com.ssafy.trycatch.roadmap.domain;

import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roadmap")
public class Roadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "node")
    private String node;

    @Lob
    @Column(name = "edge")
    private String edge;

    @Size(max = 50)
    @Column(name = "tag", length = 50)
    private String tag;

    @Size(max = 50)
    @Column(name = "title", length = 50)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Roadmap(Long id, String node, String edge, String tag, String title, User user) {
        this.id = id;
        this.node = node;
        this.edge = edge;
        this.tag = tag;
        this.title = title;
        this.user = user;
    }
}