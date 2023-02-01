package com.ssafy.trycatch.common.domain;

import com.ssafy.trycatch.user.domain.User;
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

    @Size(max = 10)
    @Column(name = "target_type", length = 10)
    private String targetType;

    @Column(name = "activated")
    private Boolean activated;
}