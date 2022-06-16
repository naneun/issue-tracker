package com.team03.issuetracker.issue.domain.dto;

import com.team03.issuetracker.issue.domain.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@AllArgsConstructor
public class LabelUpdateRequest {

	private String title;

	private String description;

	private String backgroundColor;

	public LabelUpdateRequest merge(Label label) {
		if (Strings.isBlank(this.title)) {
			this.title = label.getTitle();
		}
		if (Strings.isBlank(this.description)) {
			this.description = label.getDescription();
		}
		if (Strings.isBlank(this.backgroundColor)) {
			this.backgroundColor = label.getBackgroundColor();
		}
		return this;
	}

}
