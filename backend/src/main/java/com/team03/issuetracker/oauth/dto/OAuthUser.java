package com.team03.issuetracker.oauth.dto;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OAuthUser {

    private String serialNumber;

    private ResourceServer resourceServer;

    private String name;

    private String email;

    private String profileImage;

    private OAuthAccessToken oAuthAccessToken;

    public Member toEntity() {
        return Member.builder()
                .serialNumber(serialNumber)
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .oAuthAccessToken(oAuthAccessToken.getAccessToken())
                .resourceServer(ResourceServer.GITHUB)
                .build();
    }
}
