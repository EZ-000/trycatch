package com.ssafy.trycatch.roadmap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.ssafy.trycatch.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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