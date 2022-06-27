package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.dto.comment.request.CommentAddRequest;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentModifyRequest;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentResponse;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

	/**
	 * issueId 에 해당하는 이슈에 등록된 코멘트 리스트를 조회한다.
	 *
	 * @param issueId
	 * @param pageable
	 * @return
	 */
	Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable);

	/**
	 * issueId 에 해당하는 이슈에 요청 받은 코멘트를 등록한다.
	 *
	 * @param commentAddRequest { }
	 */
	CommentResponse addComment(Long issueId, CommentAddRequest commentAddRequest);

	/**
	 * issueId 에 해당하는 이슈에 등록된 요청 받은 코멘트를 수정한다.
	 *
	 * @param commentModifyRequest { String content; }
	 */
	CommentResponse modifyComment(Long issueId, Long commentId,
		CommentModifyRequest commentModifyRequest);

	/**
	 * issueId 에 해당하는 이슈에 등록된 commentId 에 해당하는 코멘트를 삭제한다.
	 *
	 * @param commentId
	 */
	CommentResponse deleteCommentById(Long issueId, Long commentId);

	/**
	 * commentId 에 해당하는 코멘트에 emojiId 에 해당하는 이모지를 등록한다.
	 *
	 * @param commentId
	 * @param emojiId
	 */
	CommentResponse addEmojiToComment(Long commentId, Long emojiId);
}
