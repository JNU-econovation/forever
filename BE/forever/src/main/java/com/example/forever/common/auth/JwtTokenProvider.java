package com.example.forever.common.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 7;  // 7일

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String createAccessToken(Long userId, String role) {
        return createToken(userId, role, ACCESS_TOKEN_VALIDITY);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, null, REFRESH_TOKEN_VALIDITY);
    }

    private String createToken(Long userId, String role, long validity) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("memberId", userId);
        if (role != null) {
            claims.put("role", role);
        }

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            String pureToken = extractBearerToken(token);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(pureToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private String extractBearerToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }


    public Long getMemberIdFromToken(String token) {
        String pureToken = extractBearerToken(token);
        return Long.valueOf(
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(pureToken)
                        .getBody()
                        .get("memberId").toString()
        );
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
