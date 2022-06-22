package com.team03.issuetracker.milestone.domain.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneModifyRequest {

    @NotBlank
    private String title;

    private String description;

    private LocalDate dueDate;
}
