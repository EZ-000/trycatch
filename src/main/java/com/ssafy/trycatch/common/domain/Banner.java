package com.ssafy.trycatch.common.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "start_from")
    private LocalDate startFrom;

    @Column(name = "end_at")
    private LocalDate endAt;

}