package com.team03.issuetracker.issue.domain.dto.comment.request;

import com.team03.issuetracker.issue.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

	private final String content;

	public Comment toEntity() {
		return Comment.builder()
			.content(content)
			.build();
	}
}
