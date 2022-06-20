package com.team03.issuetracker.oauth.controller;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OauthService;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.provider.JwtProvider;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.ACCESS_TOKEN;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final JwtProvider jwtProvider;

	private final Map<String, OauthService> oauthServicePicker;

	@GetMapping("/{resource-server}/login")
	public ResponseEntity<Void> login(@PathVariable(name = "resource-server") String resourceServer, String code,
		HttpServletResponse response) {

		OauthService oauthService = oauthServicePicker.get(resourceServer);
		OauthAccessToken accessToken = oauthService.obtainAccessToken(code);
		Member oauthMember = oauthService.obtainUserInfo(accessToken);
		String jwtToken = jwtProvider.makeJwtToken(oauthMember);
		System.out.println("===============" + jwtToken);

		response.setHeader(ACCESS_TOKEN, jwtToken);
		loginService.login(oauthMember);

		return ResponseEntity.ok().build();
	}
}
