package com.team03.issuetracker.milestone.domain.dto;

import com.team03.issuetracker.milestone.domain.Milestone;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MilestoneResponse {

	private String title;
	private String description;
	private LocalDate dueDate;
	private Long openIssueCount;
	private Long closedIssueCount;

	public MilestoneResponse(Milestone milestone) {
		this.title = milestone.getTitle();
		this.description = milestone.getDescription();
		this.dueDate = milestone.getDueDate();
		this.openIssueCount = milestone.getOpenIssueCount();
		this.closedIssueCount = milestone.getClosedIssueCount();
	}

}
