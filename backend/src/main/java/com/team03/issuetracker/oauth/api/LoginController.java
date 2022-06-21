package com.team03.issuetracker.oauth.api;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OAuthService;
import com.team03.issuetracker.oauth.common.AccessTokenHeader;
import com.team03.issuetracker.oauth.common.LoginMember;
import com.team03.issuetracker.oauth.common.RefreshTokenHeader;
import com.team03.issuetracker.oauth.dto.OAuthAccessToken;
import com.team03.issuetracker.oauth.dto.OAuthUser;
import com.team03.issuetracker.oauth.provider.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.ACCESS_TOKEN;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;

	private final Map<String, OAuthService> oAuthServiceMapper;

	private final LoginService loginService;

	@GetMapping("/{resource-server}/callback")
	public ResponseEntity<LoginMemberResponse> login(@PathVariable(name = "resource-server") String resourceServer,
		String code) {

		OAuthService oAuthService = oAuthServiceMapper.get(resourceServer);
		OAuthAccessToken accessToken = oAuthService.obtainAccessToken(code);
		OAuthUser oAuthUser = oAuthService.obtainUserInfo(accessToken);

		Member member = loginService.login(oAuthUser);

		return ResponseEntity.ok()
			.header(ACCESS_TOKEN, jwtTokenProvider.makeJwtAccessToken(member))
			.header(REFRESH_TOKEN, jwtTokenProvider.makeJwtRefreshToken(member))
			.body(LoginMemberResponse.from(member));
	}

	@GetMapping("/update/jwt-access-token")
	public ResponseEntity<Void> renewJwtAccessToken(@AccessTokenHeader String accessToken,
		@RefreshTokenHeader String refreshToken) {

		Member member = null;
		try {
			jwtTokenProvider.verifyToken(accessToken);

		} catch (ExpiredJwtException e) {
			Long memberId = Long.parseLong(e.getClaims().getAudience());
			member = loginService.findById(memberId);
			//TODO : 꺼낸 refresh 토큰과 요청 시 받은 refresh 토큰을 비교하여 일치하는지 검사
			jwtTokenProvider.validateRefreshToken(memberId, refreshToken);
			return ResponseEntity.ok().header(ACCESS_TOKEN, jwtTokenProvider.makeJwtAccessToken(member)).build();

		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return null;
	}
}
