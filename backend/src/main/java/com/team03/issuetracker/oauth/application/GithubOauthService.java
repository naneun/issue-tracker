package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.dto.GithubUserInfo;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.exception.OauthException;
import com.team03.issuetracker.oauth.properties.OauthProperties;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("github")
public class GithubOauthService implements OauthService {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUri;
	private final String userInfoUri;

	@Autowired
	public GithubOauthService(OauthProperties properties) {
		this.clientId = properties.getGithubClientId();
		this.clientSecret = properties.getGithubClientSecret();
		this.accessTokenUri = properties.getGithubAccessTokenUri();
		this.userInfoUri = properties.getGithubUserInfoUri();
	}

	// Todo: state을 검증하는 과정을 넣으려면 애초에 code에 대한 요청도 서버에서 해야하는건가?
	@Override
	public OauthAccessToken obtainAccessToken(String code) {

		Map<String, String> body = new HashMap<>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("code", code);

		return WebClient.create().post()
			.uri(accessTokenUri)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(body)
			.retrieve()
			.bodyToMono(OauthAccessToken.class)
			.blockOptional()
			.orElseThrow(OauthException::new);
	}

	@Override
	public OauthAccessToken renewAccessToken() {
		return null;
	}

	@Override
	public Member obtainUserInfo(OauthAccessToken accessToken) {

		GithubUserInfo userInfo = WebClient.create().get()
			.uri(userInfoUri)
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
			.retrieve()
			.bodyToMono(GithubUserInfo.class)
			.blockOptional()
			.orElseThrow(OauthException::new);

		return userInfo.toEntity(accessToken);
	}
}
