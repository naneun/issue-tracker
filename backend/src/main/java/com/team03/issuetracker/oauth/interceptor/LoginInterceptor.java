package com.team03.issuetracker.oauth.interceptor;

import com.team03.issuetracker.oauth.provider.JwtTokenProvider;
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
import static com.team03.issuetracker.oauth.utils.OAuthUtils.LOGIN_ID;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return validateJwtToken(request, response, header);
    }

    private boolean validateJwtToken(HttpServletRequest request, HttpServletResponse response, String header) {
        if (header.isBlank() || !header.startsWith(BEARER)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        String jwtToken = header.replaceFirst(BEARER, Strings.EMPTY).trim();
        Claims claims = null;

        try {
            claims = jwtProvider.verifyToken(jwtToken);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        } catch (JwtException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        request.setAttribute(LOGIN_ID, claims.getAudience());

        return true;
    }
}
