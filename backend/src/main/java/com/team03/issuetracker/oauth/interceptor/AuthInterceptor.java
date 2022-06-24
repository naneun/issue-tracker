package com.team03.issuetracker.oauth.interceptor;

import static com.team03.issuetracker.oauth.utils.AuthUtils.BEARER;

import com.team03.issuetracker.oauth.application.LoginService;
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

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtProvider;
    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return validateJwtToken(response, header);
    }

    private boolean validateJwtToken(HttpServletResponse response, String header) {
        if (header.isBlank() || !header.startsWith(BEARER)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        Claims claims;

        try {
            String jwtToken = header.replaceFirst(BEARER, Strings.EMPTY).trim();
            claims = jwtProvider.verifyToken(jwtToken);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        } catch (JwtException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        loginService.updateLoginMemberById(Long.parseLong(claims.getAudience()));

        return true;
    }
}
