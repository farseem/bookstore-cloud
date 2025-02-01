package com.farsim.pet.bookstore.us.security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisTokenService {

    private final StringRedisTemplate redisTemplate;

    public RedisTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeToken(String username, String token) {
        redisTemplate.opsForValue().set("JWT_" + username, token, Duration.ofDays(1));
    }

    public void deleteToken(String username) {
        redisTemplate.delete("JWT_" + username);
    }
}
