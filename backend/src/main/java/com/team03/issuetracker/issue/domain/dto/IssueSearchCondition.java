package com.team03.issuetracker.issue.domain.dto;

import com.team03.issuetracker.issue.domain.IssueState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueSearchCondition {

    private IssueState state;

    private Long creatorId;

    private Long labelId;

    private Long milestoneId;

    public static IssueSearchCondition of(IssueState state, Long creatorId, Long labelId, Long milestoneId) {
        return new IssueSearchCondition(state, creatorId, labelId, milestoneId);
    }
}
