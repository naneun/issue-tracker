package com.team03.issuetracker.oauth.provider;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.ACCESS_TOKEN;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.RESOURCE_SERVER;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.SERIAL_NUMBER;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.TOKEN_TYPE;

@Component
public class JwtTokenProvider {

    private final String issuer;

    private final String secretKey;

    public JwtTokenProvider(JwtProperties properties) {
        this.issuer = properties.getIssuer();
        this.secretKey = properties.getSecretKey();
    }

    public String makeJwtAccessToken(LoginMemberResponse loginMemberResponse) {

        return Jwts.builder()
                .setAudience(loginMemberResponse.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)))
                .claim(SERIAL_NUMBER, loginMemberResponse.getSerialNumber())
                .claim(RESOURCE_SERVER, loginMemberResponse.getResourceServer())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public String makeJwtRefreshToken(LoginMemberResponse memberDto) {

        return Jwts.builder()
                .setAudience(memberDto.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusWeeks(2L)))
                .claim(SERIAL_NUMBER, memberDto.getSerialNumber())
                .claim(RESOURCE_SERVER, memberDto.getResourceServer())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public Claims verifyToken(String jwtToken) {

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
