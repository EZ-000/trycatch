package com.ssafy.trycatch.common.domain;

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
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;

    @Column(name = "activated")
    private Boolean activated;
}
