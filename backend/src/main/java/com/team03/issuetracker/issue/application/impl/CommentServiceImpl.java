package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.CommentService;
import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentAddRequest;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentModifyRequest;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentResponse;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentSimpleResponse;
import com.team03.issuetracker.issue.exception.CommentException;
import com.team03.issuetracker.issue.exception.EmojiException;
import com.team03.issuetracker.issue.exception.IssueException;
import com.team03.issuetracker.issue.repository.CommentRepository;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import com.team03.issuetracker.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;
	private final EmojiRepository emojiRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByIssueId(issueId, pageable);
		return comments.map(CommentSimpleResponse::from);
	}

	@Override
	@Transactional
	public CommentResponse addComment(Long issueId, CommentAddRequest commentAddRequest) {
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(IssueException::new);

		Comment comment = commentAddRequest.toEntity();
		commentRepository.save(comment);

		issue.appendComment(comment);

		return CommentResponse.from(comment);
	}

	@Override
	@Transactional
	public CommentResponse modifyComment(Long issueId, Long commentId,
		CommentModifyRequest commentModifyRequest) {

		Comment comment = commentRepository.findById(issueId)
			.orElseThrow(CommentException::new);

		Comment modified = commentModifyRequest.toEntity();

		return CommentResponse.from(comment.merge(modified));
	}

	@Override
	@Transactional
	public CommentResponse deleteComment(Long issueId, Long commentId) {
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(IssueException::new);

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(CommentException::new);

		issue.removeComment(comment);

		commentRepository.deleteById(comment.getId());

		return CommentResponse.from(comment);
	}

	@Override
	@Transactional
	public CommentResponse addEmojiToComment(Long commentId, Long emojiId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(CommentException::new);

		Emoji emoji = emojiRepository.findById(emojiId)
			.orElseThrow(EmojiException::new);

		comment.addEmoji(emoji);

		return CommentResponse.from(comment);
	}
}
