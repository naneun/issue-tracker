package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;

public interface OauthService {

	OauthAccessToken obtainAccessToken(String code);

	OauthAccessToken renewAccessToken();

	Member obtainUserInfo(OauthAccessToken accessToken);
}
