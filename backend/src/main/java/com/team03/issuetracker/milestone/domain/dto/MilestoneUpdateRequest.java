package com.team03.issuetracker.milestone.domain.dto;

import com.team03.issuetracker.milestone.domain.Milestone;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@AllArgsConstructor
public class MilestoneUpdateRequest {

	private String title;

	private String description;

	private LocalDate dueDate;

	public MilestoneUpdateRequest merge(Milestone milestone) {
		if (Strings.isBlank(this.title)) {
			this.title = milestone.getTitle();
		}
		if (Strings.isBlank(this.description)) {
			this.description = milestone.getDescription();
		}
		if (this.dueDate == null) {
			this.dueDate = milestone.getDueDate();
		}
		return this;
	}
}
