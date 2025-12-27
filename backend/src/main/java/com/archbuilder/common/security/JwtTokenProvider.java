package com.archbuilder.common.security;

import com.archbuilder.user.domain.model.vo.PlanType;
import com.archbuilder.user.domain.model.vo.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String createAccessToken(String userId, PlanType planType, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "ACCESS");
        claims.put("plan", planType.name());
        claims.put("roles", roles.stream()
                .map(Role::name)
                .collect(Collectors.toSet()));
        return createToken(userId, accessTokenValidity, claims);
    }

    public String createRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "REFRESH");
        return createToken(userId, refreshTokenValidity, claims);
    }

    private String createToken(String userId, long expireMs, Map<String, ?> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userId)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expireMs)))
                .signWith(key)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
