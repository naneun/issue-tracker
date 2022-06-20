package com.team03.issuetracker.oauth.interceptor;

import com.team03.issuetracker.oauth.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.BEARER;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		return validateJwtToken(response, header);
	}

	private boolean validateJwtToken(HttpServletResponse response, String header) {
		if (header.isBlank() || !header.startsWith(BEARER)) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return false;
		}

		String jwtToken = header.replaceFirst(BEARER, Strings.EMPTY).trim();

		try {
			Claims claims = jwtProvider.verifyToken(jwtToken);
			//			request.setAttribute("memberId", claims.getAudience());
			// Todo : Resolver 사용하기
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		} catch (JwtException e) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return false;
		}

		return true;
	}

}
