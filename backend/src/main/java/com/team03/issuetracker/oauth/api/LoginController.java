package com.team03.issuetracker.oauth.api;

import com.team03.issuetracker.common.application.RedisService;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.application.LoginService;
import com.team03.issuetracker.oauth.application.OAuthService;
import com.team03.issuetracker.oauth.common.AccessTokenHeader;
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

	private final RedisService redisService;

	@GetMapping("/{resource-server}/callback")
	public ResponseEntity<LoginMemberResponse> login(@PathVariable(name = "resource-server") String resourceServer,
		String code) {

		OAuthService oAuthService = oAuthServiceMapper.get(resourceServer);

		OAuthAccessToken accessToken = oAuthService.obtainAccessToken(code);
		OAuthUser oAuthUser = oAuthService.obtainUserInfo(accessToken);

		Member member = loginService.save(oAuthUser);
		String jwtAccessToken = jwtTokenProvider.makeJwtAccessToken(member);
		String jwtRefreshToken = jwtTokenProvider.makeJwtRefreshToken(member);

		redisService.saveJwtRefreshToken(member.getId(), jwtRefreshToken);

		return ResponseEntity.ok()
			.header(ACCESS_TOKEN, jwtAccessToken)
			.header(REFRESH_TOKEN, jwtRefreshToken)
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

			// TODO: verifyMatchingRefreshToken() 메서드가 catch 문에 걸리도록 로직 수정 필요
			redisService.verifyMatchingRefreshToken(memberId, refreshToken);
			member = loginService.findById(memberId);

			return ResponseEntity.ok().header(ACCESS_TOKEN, jwtTokenProvider.makeJwtAccessToken(member)).build();

		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return null;
	}
}
