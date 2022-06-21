package com.team03.issuetracker.common.application;

import com.team03.issuetracker.oauth.exception.JwtException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	public void saveJwtRefreshToken(Long memberId, String refreshToken) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(String.valueOf(memberId), refreshToken, Duration.ofDays(14));
	}

	public String getJwtRefreshToken(Long memberId) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		return values.get(String.valueOf(memberId));
	}

	public void removeJwtRefreshToken(Long memberId) {
		redisTemplate.delete(String.valueOf(memberId));
	}

	public void verifyMatchingRefreshToken(Long memberId, String refreshToken) {
		String savedRefreshToken = redisTemplate.opsForValue().get(String.valueOf(memberId));

		if (!refreshToken.equals(savedRefreshToken)) {
			throw new JwtException();
		}
	}
}
