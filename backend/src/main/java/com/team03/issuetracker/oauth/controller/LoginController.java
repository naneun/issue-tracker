package com.team03.issuetracker.oauth.controller;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OauthService;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.provider.JwtProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.ACCESS_TOKEN;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final JwtProvider jwtProvider;

	private final Map<String, OauthService> oauthServicePicker;

	@GetMapping("/{resource-server}/login")
	public ResponseEntity<LoginMemberResponse> login(@PathVariable(name = "resource-server") String resourceServer,
		String code) {

		OauthService oauthService = oauthServicePicker.get(resourceServer);
		OauthAccessToken accessToken = oauthService.obtainAccessToken(code);
		LoginMemberResponse memberDto = oauthService.obtainUserInfo(accessToken);

		return ResponseEntity.ok()
			.header(ACCESS_TOKEN, jwtProvider.makeJwtToken(memberDto))
			.header(REFRESH_TOKEN, jwtProvider.makeJwtRefreshToken(memberDto))
			.body(memberDto);
	}

	// TODO
	@GetMapping
	public ResponseEntity<Void> updateJwtAccessToken(String refreshToken) {
		return null;
	}
}
