package com.team03.issuetracker.issue.domain.dto.comment.request;

public class CommentModifyRequest extends CommentRequestDto {

    public CommentModifyRequest(Long id, String content) {
        super(id, content);
    }
}
