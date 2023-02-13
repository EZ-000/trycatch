package com.ssafy.trycatch.feed.domain;

import com.ssafy.trycatch.common.domain.Company;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @Size(max = 256)
    @Column(name = "title", length = 256)
    private String title;

    @Lob
    @Column(name = "url")
    private String url;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "feed")
    @ToString.Exclude
    @Builder.Default
    private Set<Read> reads = new LinkedHashSet<>();

    @Size(max = 128)
    @NotNull
    @Column(name = "es_id", nullable = false, length = 128)
    private String esId;

}
