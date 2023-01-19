package com.ssafy.trycatch.feed.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
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
@Table(name = "feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude
    private Company company;

    @Size(max = 80)
    @Column(name = "title", length = 80)
    private String title;

    @Size(max = 100)
    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "feed")
    @ToString.Exclude
    private Set<Read> reads = new LinkedHashSet<>();

}