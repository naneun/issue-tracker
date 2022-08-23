package com.team03.issuetracker.issue.domain.dto.issue.request;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssueRequestDto {

	/* final 앲애고 기본 생성자 만들면 작동하는데, 이 방법이 맞나..? */
	private String title;
	private String content;

	private Long labelId;
	private Long milestoneId;
	private Long assigneeId;

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
