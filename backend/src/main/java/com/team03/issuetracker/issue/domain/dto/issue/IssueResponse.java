package com.team03.issuetracker.issue.domain.dto.issue;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueResponse {

	private Long id;

	private String title;

	private String content;

	private IssueState state;

	private Label label;

	private Milestone milestone;

	private Member assignee;

	public static IssueResponse from(Issue issue) {
		return new IssueResponse(
			issue.getId(),
			issue.getTitle(),
			issue.getContent(),
			issue.getState(),
			issue.getLabel(),
			issue.getMilestone(),
			issue.getAssignee()
		);
	}
}
