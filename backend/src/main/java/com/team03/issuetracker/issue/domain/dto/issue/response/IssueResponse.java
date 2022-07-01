package com.team03.issuetracker.issue.domain.dto.issue.response;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class IssueResponse {

	private Long id;

	private String title;

	private String content;

	private IssueState state;

	private LabelResponseOfIssue label;

	private MilestoneResponseOfIssue milestone;

	private AssigneeResponseOfIssue assignee;

	public static IssueResponse from(Issue issue) {
		return new IssueResponse(
			issue.getId(),
			issue.getTitle(),
			issue.getContent(),
			issue.getState(),
			issue.getLabel() == null ? null : LabelResponseOfIssue.from(issue.getLabel()),
			issue.getMilestone() == null ? null
				: MilestoneResponseOfIssue.from(issue.getMilestone()),
			issue.getAssignee() == null ? null : AssigneeResponseOfIssue.from(issue.getAssignee())
		);
	}

	@Getter
	@RequiredArgsConstructor
	private static class LabelResponseOfIssue {

		private final Long labelId;
		private final String labelTitle;

		public static LabelResponseOfIssue from(Label label) {
			return new LabelResponseOfIssue(label.getId(), label.getTitle());
		}
	}

	@Getter
	@RequiredArgsConstructor
	private static class MilestoneResponseOfIssue {

		private final Long milestoneId;
		private final String milestoneTitle;

		public static MilestoneResponseOfIssue from(Milestone milestone) {
			return new MilestoneResponseOfIssue(milestone.getId(), milestone.getTitle());
		}
	}

	@Getter
	@RequiredArgsConstructor
	private static class AssigneeResponseOfIssue {

		private final Long assigneeId;
		private final String assigneeName;

		public static AssigneeResponseOfIssue from(Member assignee) {
			return new AssigneeResponseOfIssue(assignee.getId(), assignee.getName());
		}
	}
}
