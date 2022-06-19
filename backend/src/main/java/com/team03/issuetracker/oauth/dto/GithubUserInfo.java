package com.team03.issuetracker.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.Getter;

@Getter
public class GithubUserInfo {

	@JsonProperty(value = "id")
	private String serialNumber;

	@JsonProperty(value = "login")
	private String name;

	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "avatar_url")
	private String profileImage;

	public Member toEntity(OauthAccessToken accessToken) {
		return Member.builder()
			.serialNumber(serialNumber)
			.name(name)
			.email(email)
			.profileImage(profileImage)
			.oauthAccessToken(accessToken.getAccessToken())
			.resourceServer(ResourceServer.GITHUB)
			.build();
	}
}
