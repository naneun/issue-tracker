package com.team03.issuetracker.issue.domain.dto.issue;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class IssueDetailResponse {

    private LabelResponseOfDetailIssue label;
    private MilestoneResponseOfDetailIssue milestone;
    private AssigneeResponseOfDetailIssue assignee;

    public static IssueDetailResponse of(Issue issue) {
        return new IssueDetailResponse(
            LabelResponseOfDetailIssue.from(issue.getLabel()),
            MilestoneResponseOfDetailIssue.from(issue.getMilestone()),
            AssigneeResponseOfDetailIssue.from(issue.getAssignee())
        );
    }

    @Getter
    @RequiredArgsConstructor
    private static class LabelResponseOfDetailIssue {

        private final Long labelId;
        private final String labelTitle;

        public static LabelResponseOfDetailIssue from(Label label) {
            return new LabelResponseOfDetailIssue(label.getId(), label.getTitle());
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class MilestoneResponseOfDetailIssue {

        private final Long milestoneId;
        private final String milestoneTitle;

        public static MilestoneResponseOfDetailIssue from(Milestone milestone) {
            return new MilestoneResponseOfDetailIssue(milestone.getId(), milestone.getTitle());
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class AssigneeResponseOfDetailIssue {

        private final Long assigneeId;
        private final String assigneeName;

        public static AssigneeResponseOfDetailIssue from(Member assignee) {
            return new AssigneeResponseOfDetailIssue(assignee.getId(), assignee.getName());
        }
    }
}
