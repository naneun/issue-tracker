package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.dto.GoogleUserInfo;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.exception.OauthException;
import com.team03.issuetracker.oauth.properties.OauthProperties;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("google")
public class GoogleOauthService implements OauthService {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUri;
	private final String userInfoUri;
	private final String redirectUri;

	@Autowired
	public GoogleOauthService(OauthProperties properties) {
		this.clientId = properties.getGoogleClientId();
		this.clientSecret = properties.getGoogleClientSecret();
		this.accessTokenUri = properties.getGoogleAccessTokenUri();
		this.userInfoUri = properties.getGoogleUserInfoUri();
		this.redirectUri = properties.getGoogleRedirectUri();
	}

	@Override
	public OauthAccessToken obtainAccessToken(String code) {
		Map<String, String> body = new HashMap<>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("code", code);
		body.put("grant_type", "authorization_code");
		body.put("redirect_uri", redirectUri);

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
		GoogleUserInfo userInfo = WebClient.create().get()
			.uri(userInfoUri)
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
			.retrieve()
			.bodyToMono(GoogleUserInfo.class)
			.blockOptional()
			.orElseThrow(OauthException::new);

		return userInfo.toEntity(accessToken);
	}
}
