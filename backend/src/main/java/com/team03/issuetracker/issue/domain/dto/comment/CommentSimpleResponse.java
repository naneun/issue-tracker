package com.team03.issuetracker.issue.domain.dto.comment;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentSimpleResponse {

    private WriterResponseOfSimpleComment writer;

    private LocalDateTime createdAt;

    private String content;

    public static CommentSimpleResponse from(Comment comment) {
        return new CommentSimpleResponse(WriterResponseOfSimpleComment.from(comment.getWriter()),
            comment.getCreatedDate(), comment.getContent());
    }

    @Getter
    @RequiredArgsConstructor
    private static class WriterResponseOfSimpleComment {

        private final Long writerId;
        private final String writerName;

        public static WriterResponseOfSimpleComment from(Member writer) {
            return new WriterResponseOfSimpleComment(writer.getId(), writer.getName());
        }
    }
}
