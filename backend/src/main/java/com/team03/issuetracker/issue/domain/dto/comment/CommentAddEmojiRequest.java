package com.team03.issuetracker.issue.domain.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentAddEmojiRequest {

    @JsonProperty
    private Long emojiId;
}
