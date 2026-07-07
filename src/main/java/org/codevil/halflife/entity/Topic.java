package org.codevil.halflife.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    private String resourceLink;

    @Column(nullable = false)
    private LocalDate dateLearned;

    @Column(nullable = false)
    private LocalDate lastRevisedDate;

    @Column(nullable = false)
    private int intervalDays;

    @Column(nullable = false)
    private LocalDate nextRevisionDate;
}