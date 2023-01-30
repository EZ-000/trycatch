package com.ssafy.trycatch.qna.domain;

import com.ssafy.trycatch.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Size(max = 50)
    @Column(name = "title", length = 50)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "chosen")
    private Boolean chosen;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "hidden")
    private Boolean hidden;

    @OneToMany(mappedBy = "question")
    @ToString.Exclude
    private Set<Answer> answers = new LinkedHashSet<>();

    @Transient
    private List<String> tags = new ArrayList<String>();
}