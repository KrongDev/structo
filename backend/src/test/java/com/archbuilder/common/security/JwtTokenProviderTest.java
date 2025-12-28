package com.archbuilder.common.security;

import com.archbuilder.user.domain.model.vo.PlanType;
import com.archbuilder.user.domain.model.vo.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String secret = "testSecretKeyForJwtTokenProviderTest1234567890";
    private final long accessTokenValidity = 3600000;
    private final long refreshTokenValidity = 604800000;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret, accessTokenValidity, refreshTokenValidity);
    }

    @Test
    @DisplayName("should create access token with correct claims")
    void createAccessToken() {
        // given
        String userId = "user-1";
        PlanType planType = PlanType.FREE;
        Set<Role> roles = Set.of(Role.USER);

        // when
        String token = jwtTokenProvider.createAccessToken(userId, planType, roles);

        // then
        assertThat(token).isNotNull();

        Claims claims = jwtTokenProvider.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo(userId);
        assertThat(claims.get("type")).isEqualTo("ACCESS");
        assertThat(claims.get("plan")).isEqualTo(PlanType.FREE.name());
        assertThat(claims.get("roles")).asList().contains(Role.USER.name());
    }

    @Test
    @DisplayName("should create refresh token")
    void createRefreshToken() {
        // given
        String userId = "user-1";

        // when
        String token = jwtTokenProvider.createRefreshToken(userId);

        // then
        assertThat(token).isNotNull();

        Claims claims = jwtTokenProvider.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo(userId);
        assertThat(claims.get("type")).isEqualTo("REFRESH");
    }

    @Test
    @DisplayName("should get user id from token")
    void getUserId() {
        // given
        String userId = "user-1";
        String token = jwtTokenProvider.createAccessToken(userId, PlanType.FREE, Set.of(Role.USER));

        // when
        String extractedUserId = jwtTokenProvider.getUserId(token);

        // then
        assertThat(extractedUserId).isEqualTo(userId);
    }

    @Test
    @DisplayName("should validate valid token")
    void validateToken_valid() {
        // given
        String token = jwtTokenProvider.createAccessToken("user-1", PlanType.FREE, Set.of(Role.USER));

        // when
        boolean isValid = jwtTokenProvider.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("should not validate invalid token")
    void validateToken_invalid() {
        // given
        String token = "invalid-token";

        // when
        boolean isValid = jwtTokenProvider.validateToken(token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("should not validate expired token")
    void validateToken_expired() {
        // given
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(secret, 0, 0);
        String token = shortLivedProvider.createAccessToken("user-1", PlanType.FREE, Set.of(Role.USER));

        // when
        boolean isValid = shortLivedProvider.validateToken(token);

        // then
        assertThat(isValid).isFalse();
    }
}
