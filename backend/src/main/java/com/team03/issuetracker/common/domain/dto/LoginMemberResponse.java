package com.team03.issuetracker.common.domain.dto;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMemberResponse {

    private final Long id;

    private final String name;

    private final String serialNumber;

    private final ResourceServer resourceServer;

    private final String profileImage;

    private final String jwtAccessToken;

    private final String jwtRefreshToken;

    public static LoginMemberResponse of(Member member, String jwtAccessToken, String jwtRefreshToken) {

        return new LoginMemberResponse(member.getId(), member.getName(), member.getSerialNumber(),
                member.getResourceServer(), member.getProfileImage(), jwtAccessToken, jwtRefreshToken);
    }
}
