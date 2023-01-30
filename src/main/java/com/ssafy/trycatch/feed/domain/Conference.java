package com.ssafy.trycatch.feed.domain;

import com.ssafy.trycatch.common.domain.Company;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "conference")
public class Conference {
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

    @Size(max = 200)
    @Column(name = "summary", length = 200)
    private String summary;

    @Size(max = 100)
    @Column(name = "url", length = 100)
    private String url;

}