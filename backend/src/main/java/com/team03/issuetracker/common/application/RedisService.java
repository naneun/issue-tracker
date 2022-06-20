package com.team03.issuetracker.common.application;

import com.team03.issuetracker.common.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveJwtRefreshToken(String userId, String refreshToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(userId, refreshToken, Duration.ofDays(14));
    }

    public String getJwtRefreshToken(String userId) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(userId);
    }

    public void removeJwtRefreshToken(String userId) {
        redisTemplate.delete(userId);
    }

    public Member getLoginMember() {
        // TODO values.get(loginMember);
        return null;
    }
}
