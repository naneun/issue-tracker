package com.team03.issuetracker.oauth.controller;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OauthService;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	private final Map<String, OauthService> oauthServicePicker;

	@GetMapping("/{resource-server}/login")
	public void login(@PathVariable(name = "resource-server") String resourceServer, @RequestParam String code) {

		OauthService oauthService = oauthServicePicker.get(resourceServer);
		OauthAccessToken accessToken = oauthService.obtainAccessToken(code);
		Member oauthMember = oauthService.obtainUserInfo(accessToken);

		loginService.login(oauthMember);
	}
}
