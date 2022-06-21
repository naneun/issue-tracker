package com.team03.issuetracker.oauth.provider;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	private final String issuer;

	private final String secretKey;

	public JwtTokenProvider(JwtProperties properties) {
		this.issuer = properties.getIssuer();
		this.secretKey = properties.getSecretKey();
	}

	public String makeJwtAccessToken(Member member) {

		return Jwts.builder()
			.setAudience(member.getId().toString())
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

	}

	public String makeJwtRefreshToken(Member member) {

		return Jwts.builder()
			.setAudience(member.getId().toString())
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Timestamp.valueOf(LocalDateTime.now().plusWeeks(2L)))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

	}

	public Claims verifyToken(String jwtToken) {

		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwtToken)
			.getBody();
	}

	public void validateRefreshToken(Long userId, String refreshToken) {

		// Redis 에서 해당 memberId에 해당하는 refreshToken을 꺼내서 대조, 틀리면 LoginException

	}
}
