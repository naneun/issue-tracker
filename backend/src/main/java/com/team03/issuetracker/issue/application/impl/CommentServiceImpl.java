package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.CommentService;
import com.team03.issuetracker.issue.domain.dto.comment.CommentAddEmojiRequest;
import com.team03.issuetracker.issue.domain.dto.comment.CommentAddRequest;
import com.team03.issuetracker.issue.domain.dto.comment.CommentModifyRequest;
import com.team03.issuetracker.issue.domain.dto.comment.CommentResponse;
import com.team03.issuetracker.issue.domain.dto.comment.CommentSimpleResponse;
import com.team03.issuetracker.issue.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable) {
        return null;
    }

    @Override
    public CommentResponse addComment(Long issueId, CommentAddRequest commentAddRequest) {
        return null;
    }

    @Override
    public CommentResponse modifyComment(Long issueId, CommentModifyRequest commentModifyRequest) {
        return null;
    }

    @Override
    public CommentResponse deleteCommentById(Long issueId, Long commentId) {
        return null;
    }

    @Override
    public CommentResponse addEmojiToComment(CommentAddEmojiRequest commentAddEmojiRequest) {
        return null;
    }
}
