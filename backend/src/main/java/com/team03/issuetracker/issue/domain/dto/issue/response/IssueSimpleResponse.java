package com.team03.issuetracker.issue.domain.dto.issue.response;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueSimpleResponse {

    private final Long issueId;
    private final String title;
    private final String content;
    private final MilestoneResponseOfSimpleIssue milestone;
    private final LabelResponseOfSimpleIssue label;

    public IssueSimpleResponse(Issue issue) {
        this.issueId = issue.getId();
        this.title = issue.getTitle();
        this.content = issue.getContent();
        this.milestone = MilestoneResponseOfSimpleIssue.from(issue.getMilestone());
        this.label = LabelResponseOfSimpleIssue.from(issue.getLabel());
    }

    @Getter
    @RequiredArgsConstructor
    private static class MilestoneResponseOfSimpleIssue {

        private final String title;

        public static MilestoneResponseOfSimpleIssue from(Milestone milestone) {
            return new MilestoneResponseOfSimpleIssue(milestone.getTitle());
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class LabelResponseOfSimpleIssue {

        private final String title;
        private final String backgroundColor;

        public static LabelResponseOfSimpleIssue from(Label label) {
            return new LabelResponseOfSimpleIssue(label.getTitle(), label.getBackgroundColor());
        }
    }
}
