package com.team03.issuetracker.issue.domain.dto.label;

import com.team03.issuetracker.issue.domain.Label;
import lombok.Getter;

@Getter
public class LabelResponse {

	private Long id;

	private String title;

	private String description;

	private String backgroundColor;

	public LabelResponse(Label label) {
		this.id = label.getId();
		this.title = label.getTitle();
		this.description = label.getDescription();
		this.backgroundColor = label.getBackgroundColor();
	}
}
