package com.team03.issuetracker.issue.domain.dto.issue.request;

import javax.validation.constraints.NotBlank;

public class IssueModifyRequest extends IssueRequestDto {

	public IssueModifyRequest(@NotBlank Long id, @NotBlank String title,
		@NotBlank String content, Long labelId, Long milestoneId, Long assigneeId) {

		super(id, title, content, labelId, milestoneId, assigneeId);
	}
}
