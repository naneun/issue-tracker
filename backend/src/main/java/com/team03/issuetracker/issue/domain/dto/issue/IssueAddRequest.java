package com.team03.issuetracker.issue.domain.dto.issue;

import static com.team03.issuetracker.issue.domain.IssueState.OPEN;

import com.team03.issuetracker.issue.domain.IssueState;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueAddRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    private final IssueState state = OPEN;

    private final Long labelId;
    private final Long milestoneId;
    private final Long assigneeId;
}
