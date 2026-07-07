package org.codevil.halflife.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTopicRequest {
    private String title;
    private String category;
    private String resourceLink;
    private LocalDate dateLearned;
}