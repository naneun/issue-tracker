package com.team03.issuetracker.issue.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelUpdateRequest {

	private String title;
	private String description;
	private String backgroundColor;
}
