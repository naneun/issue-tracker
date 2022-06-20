package com.team03.issuetracker.issue.domain.dto.emoji;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmojiResponse {

    private String unicode;

    private String description;
}
