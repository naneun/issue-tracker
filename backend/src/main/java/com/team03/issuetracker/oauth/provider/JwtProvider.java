package com.team03.issuetracker.oauth.provider;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.ACCESS_TOKEN;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.RESOURCE_SERVER;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.SERIAL_NUMBER;
import static com.team03.issuetracker.oauth.utils.OAuthUtils.TOKEN_TYPE;

@Component
public class JwtProvider {

	private final String issuer;
	private final String secretKey;

	@Autowired
	public JwtProvider(JwtProperties properties) {
		this.issuer = properties.getIssuer();
		this.secretKey = properties.getSecretKey();
	}

	public String makeJwtToken(Member member) {
		// HMAC256
		return Jwts.builder()
			//			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)))
			.claim(SERIAL_NUMBER, member.getSerialNumber())
			.claim(RESOURCE_SERVER, member.getResourceServer())
			.claim(TOKEN_TYPE, ACCESS_TOKEN)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

	}
}
