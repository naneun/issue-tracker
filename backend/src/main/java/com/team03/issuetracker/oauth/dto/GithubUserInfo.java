package com.team03.issuetracker.oauth.dto;

import static com.team03.issuetracker.oauth.common.ResourceServer.GITHUB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class GithubUserInfo {

    @JsonProperty(value = "id")
    private String serialNumber;

    private ResourceServer resourceServer;

    @JsonProperty(value = "login")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "avatar_url")
    private String profileImage;

    private String oAuthAccessToken;

    public OAuthUser toOAuthUser(OAuthAccessToken oAuthAccessToken, String email) {
        return new OAuthUser(serialNumber, GITHUB, name, email, profileImage, oAuthAccessToken);
    }
}
