package com.team03.issuetracker.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAuthAccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    public String fullInfo() {
        return tokenType + " " + accessToken;
    }
}
