package com.team03.issuetracker.issue.domain.dto.emoji;

import com.team03.issuetracker.issue.domain.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmojiResponse {

    private String unicode;
    private String description;

    public EmojiResponse(Emoji emoji) {
        this.unicode = emoji.getUnicode();
        this.description = emoji.getDescription();
    }
}
