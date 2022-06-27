package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.CommentService;
import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentAddEmojiRequest;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentAddRequest;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentModifyRequest;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentResponse;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentSimpleResponse;
import com.team03.issuetracker.issue.exception.CommentException;
import com.team03.issuetracker.issue.exception.IssueException;
import com.team03.issuetracker.issue.repository.CommentRepository;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import com.team03.issuetracker.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;
	private final EmojiRepository emojiRepository;

	@Override
	public Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable) {

		return null;
	}

	@Override
	public CommentResponse addComment(Long issueId, CommentAddRequest commentAddRequest) {
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(IssueException::new);

		return null;
	}

	@Override
	public CommentResponse modifyComment(Long issueId, Long commentId,
		CommentModifyRequest commentModifyRequest) {

		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(IssueException::new);

		return null;
	}

	@Override
	public CommentResponse deleteCommentById(Long issueId, Long commentId) {

		return null;
	}

	@Override
	public CommentResponse addEmojiToComment(Long commentId, Long emojiId) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(CommentException::new);

		return null;
	}
}
