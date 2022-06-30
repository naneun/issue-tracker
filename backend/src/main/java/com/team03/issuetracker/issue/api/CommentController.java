package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.CommentService;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@ApiOperation(
		value = "해당 댓글에 이모지 등록",
		notes = "해당 댓글에 이모지를 등록한다.",
		produces = "application/json",
		response = CommentResponse.class
	)
	@PostMapping("/{commentId}/emojis/{emojiId}")
	public ResponseEntity<CommentResponse> addEmojiToComment(@PathVariable Long commentId,
		@PathVariable Long emojiId) {

		return ResponseEntity.ok(commentService.addEmojiToComment(commentId, emojiId));
	}
}
