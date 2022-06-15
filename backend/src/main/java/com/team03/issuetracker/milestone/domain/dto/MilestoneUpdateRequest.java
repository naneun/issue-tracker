package com.team03.issuetracker.milestone.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneUpdateRequest {

	private String title;
	private String description;
	private LocalDate dueDate;
}
