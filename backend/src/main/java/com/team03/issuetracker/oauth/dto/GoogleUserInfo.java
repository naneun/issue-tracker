package com.team03.issuetracker.oauth.dto;

import static com.team03.issuetracker.oauth.common.ResourceServer.GOOGLE;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo {

    @JsonProperty(value = "sub")
    private String serialNumber;

    private ResourceServer resourceServer;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "picture")
    private String profileImage;

    private OAuthAccessToken oAuthAccessToken;

    public OAuthUser toOAuthUser(OAuthAccessToken oAuthAccessToken) {
        return new OAuthUser(serialNumber, GOOGLE, name, email, profileImage, oAuthAccessToken);
    }
}
