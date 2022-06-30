package com.team03.issuetracker.issue.domain.dto.issue.request;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueRequestDto {

	private final String title;
	private final String content;

	private final Long labelId;
	private final Long milestoneId;
	private final Long assigneeId;

	public Issue toEntity(Label label, Milestone milestone, Member assignee) {
		return Issue.builder()
			.title(title)
			.content(content)
			.label(label)
			.milestone(milestone)
			.assignee(assignee)
			.build();
	}
}
