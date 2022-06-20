package com.team03.issuetracker.issue.domain.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class IssueAddRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long labelId;

    private Long milestoneId;

    private Long assigneeId;
}
