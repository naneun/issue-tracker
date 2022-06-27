package com.team03.issuetracker.issue.domain.dto.issue.request;

import javax.validation.constraints.NotBlank;

public class IssueModifyRequest extends IssueRequestDto {

	public IssueModifyRequest(@NotBlank String title, @NotBlank String content, Long labelId,
		Long milestoneId, Long assigneeId) {

		super(title, content, labelId, milestoneId, assigneeId);
	}
}
