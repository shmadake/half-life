package org.codevil.halflife.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TopicResponse {
    private Long id;
    private String title;
    private String category;
    private String resourceLink;
    private LocalDate dateLearned;
    private LocalDate lastRevisedDate;
    private int intervalDays;
    private LocalDate nextRevisionDate;
}