package com.team03.issuetracker.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.Getter;

@Getter
public class GoogleUserInfo {

	@JsonProperty(value = "sub")
	private String serialNumber;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "picture")
	private String profileImage;

	public Member toEntity(OAuthAccessToken accessToken) {
		return Member.builder()
			.serialNumber(serialNumber)
			.name(name)
			.email(email)
			.profileImage(profileImage)
			.oauthAccessToken(accessToken.getAccessToken())
			.resourceServer(ResourceServer.GOOGLE)
			.build();
	}
}
