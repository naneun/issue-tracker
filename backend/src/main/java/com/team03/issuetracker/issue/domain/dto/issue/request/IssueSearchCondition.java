package com.team03.issuetracker.issue.domain.dto.issue.request;

import com.team03.issuetracker.issue.domain.IssueState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueSearchCondition {

    private final IssueState state;
    private final Long creatorId;
    private final Long labelId;
    private final Long milestoneId;

    public static IssueSearchCondition of(IssueState state, Long creatorId, Long labelId,
        Long milestoneId) {

        return new IssueSearchCondition(state, creatorId, labelId, milestoneId);
    }
}
