package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.EmojiService;
import com.team03.issuetracker.issue.domain.dto.emoji.EmojiResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emojis")
@RequiredArgsConstructor
public class EmojiController {

	private final EmojiService emojiService;

	@ApiOperation(
		value = "이모지 리스트 조회",
		notes = "이모지 리스트를 조회한다.",
		produces = "application/json",
		response = EmojiResponse.class
	)
	@GetMapping
	public ResponseEntity<List<EmojiResponse>> findByState() {
		return ResponseEntity.ok(emojiService.findAll());
	}
}
