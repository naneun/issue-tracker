package com.team03.issuetracker.issue.domain.dto.comment.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

	private final Long id;
	private final String content;
}
