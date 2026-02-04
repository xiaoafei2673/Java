package com.example.firstproject.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    // 生产环境放配置文件或环境变量
    private static final String SECRET =
            "change-this-to-a-long-long-secret-key-at-least-32-bytes";

    private static final long ACCESS_EXPIRE_SECONDS = 15 * 60; // 15 分钟

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateAccessToken(Long userId) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ACCESS_EXPIRE_SECONDS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long parseUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }
}
