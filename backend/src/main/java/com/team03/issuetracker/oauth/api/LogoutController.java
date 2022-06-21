package com.team03.issuetracker.oauth.api;

import com.team03.issuetracker.oauth.application.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController {

	private final RefreshTokenService redisService;

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(Long memberId) {

		redisService.removeJwtRefreshToken(memberId);
		return ResponseEntity.ok().build();
	}
}
