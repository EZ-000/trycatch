package com.ssafy.trycatch.common.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "reporter", nullable = false)
    private Long reporter;

    @Size(max = 10)
    @NotNull
    @Column(name = "target_type", nullable = false, length = 10)
    private String targetType;

    @NotNull
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "create_at", nullable = false)
    private Instant createAt;

}