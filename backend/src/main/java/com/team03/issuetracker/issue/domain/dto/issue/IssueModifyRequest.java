package com.team03.issuetracker.issue.domain.dto.issue;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueModifyRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long labelId;
    private Long milestoneId;
    private Long assigneeId;
}
