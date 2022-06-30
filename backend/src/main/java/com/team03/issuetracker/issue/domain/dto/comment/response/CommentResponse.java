package com.team03.issuetracker.issue.domain.dto.comment.response;

import com.team03.issuetracker.issue.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	private String content;

	public static CommentResponse from(Comment comment) {
		return new CommentResponse(comment.getId(), comment.getContent());
	}
}
