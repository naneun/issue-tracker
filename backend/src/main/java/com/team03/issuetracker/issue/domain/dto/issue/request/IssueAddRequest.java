package com.team03.issuetracker.issue.domain.dto.issue.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssueAddRequest extends IssueRequestDto {

	public IssueAddRequest(@NotBlank String title, @NotBlank String content, Long labelId,
		Long milestoneId, Long assigneeId) {

		super(title, content, labelId, milestoneId, assigneeId);
	}
}
