package com.team03.issuetracker.issue.domain.dto.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team03.issuetracker.issue.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentAddRequest {

    @JsonProperty
    private String content;

    public Comment toEntity() {
        return Comment.builder()
            .content(content)
            .build();
    }
}
