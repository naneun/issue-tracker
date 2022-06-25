package com.team03.issuetracker.issue.domain.dto.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentModifyRequest {

    @JsonProperty
    private String content;
}
