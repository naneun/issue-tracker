package com.team03.issuetracker.oauth.api;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OAuthService;
import com.team03.issuetracker.oauth.application.RefreshTokenService;
import com.team03.issuetracker.oauth.common.AccessTokenHeader;
import com.team03.issuetracker.oauth.common.RefreshTokenHeader;
import com.team03.issuetracker.oauth.dto.OAuthAccessToken;
import com.team03.issuetracker.oauth.dto.OAuthUser;
import com.team03.issuetracker.oauth.provider.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;
	private final Map<String, OAuthService> oAuthServiceMapper;
	private final LoginService loginService;
	private final RefreshTokenService refreshTokenService;

	@GetMapping("/{resource-server}/callback")
	public ResponseEntity<LoginMemberResponse> login(
		@PathVariable(name = "resource-server") String resourceServer,
		String code) {

		OAuthService oAuthService = oAuthServiceMapper.get(resourceServer);

		OAuthAccessToken accessToken = oAuthService.obtainAccessToken(code);
		OAuthUser oAuthUser = oAuthService.obtainUserInfo(accessToken);

		Member member = loginService.save(oAuthUser);

		String jwtAccessToken = jwtTokenProvider.makeJwtAccessToken(member);
		String jwtRefreshToken = jwtTokenProvider.makeJwtRefreshToken(member);

		refreshTokenService.saveJwtRefreshToken(member.getId(), jwtRefreshToken);

		return ResponseEntity.ok()
			.body(LoginMemberResponse.of(member, jwtAccessToken, jwtRefreshToken));
	}

	@GetMapping("/update/jwt-access-token")
	public ResponseEntity<LoginMemberResponse> renewJwtAccessToken(
		@AccessTokenHeader String accessToken,
		@RefreshTokenHeader String refreshToken) {
		log.info("@@@@@@@@@@@ {}", accessToken);
		/* AccessTokenResolver를 통해 request.getHeader("access_token") 값을 꺼내서 String accessToken에 넣어준다 */

		String jwtAccessToken = null;
		Member member = null;

		try {
			jwtTokenProvider.verifyToken(accessToken);
		} catch (ExpiredJwtException e) {
			Long memberId = Long.parseLong(e.getClaims().getAudience());
			refreshTokenService.verifyMatchingRefreshToken(memberId, refreshToken);
			member = loginService.findById(memberId);
			jwtAccessToken = jwtTokenProvider.makeJwtAccessToken(member);
		}

		return ResponseEntity.ok().body(LoginMemberResponse.of(member, jwtAccessToken, null));
	}
}
