package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.dto.emoji.EmojiResponse;
import java.util.List;

public interface EmojiService {

	List<EmojiResponse> findAll();
}
