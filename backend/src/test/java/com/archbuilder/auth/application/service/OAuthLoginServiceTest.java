package com.archbuilder.auth.application.service;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.dto.OAuthUserInfo;
import com.archbuilder.auth.domain.model.RefreshToken;
import com.archbuilder.auth.domain.repository.RefreshTokenRepository;
import com.archbuilder.common.security.JwtTokenProvider;
import com.archbuilder.user.application.command.CreateUserCommand;
import com.archbuilder.user.application.usecase.CreateUserUseCase;
import com.archbuilder.user.application.usecase.GetUserUseCase;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthLoginServiceTest {

    @InjectMocks
    private OAuthLoginService oAuthLoginService;

    @Mock
    private GetUserUseCase getUserUseCase;
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("should login existing user")
    void login_existing() {
        // given
        OAuthUserInfo userInfo = new OAuthUserInfo("test@example.com", "Test", AuthProvider.GOOGLE);

        // Use factory method
        User user = User.createOAuth(
                userInfo.email(),
                userInfo.name(),
                userInfo.provider(),
                Set.of(Role.USER));

        given(getUserUseCase.findByEmail(userInfo.email())).willReturn(Optional.of(user));
        given(jwtTokenProvider.createAccessToken(any(), any(), any())).willReturn("access-token");
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("refresh-token");

        // Mock claims for saveRefreshToken
        Claims claims = mock(Claims.class);
        given(claims.getExpiration()).willReturn(Date.from(Instant.now().plusSeconds(3600)));
        given(claims.getIssuedAt()).willReturn(Date.from(Instant.now()));
        given(jwtTokenProvider.getClaims(anyString())).willReturn(claims);

        // when
        AuthResult result = oAuthLoginService.execute(userInfo);

        // then
        assertThat(result.accessToken()).isEqualTo("access-token");
        assertThat(result.refreshToken()).isEqualTo("refresh-token");
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("should create and login new user")
    void login_new() {
        // given
        OAuthUserInfo userInfo = new OAuthUserInfo("new@example.com", "New", AuthProvider.GOOGLE);

        // Use factory method
        User user = User.createOAuth(
                userInfo.email(),
                userInfo.name(),
                userInfo.provider(),
                Set.of(Role.USER));

        given(getUserUseCase.findByEmail(userInfo.email())).willReturn(Optional.empty());
        given(createUserUseCase.execute(any(CreateUserCommand.class))).willReturn(user);

        given(jwtTokenProvider.createAccessToken(any(), any(), any())).willReturn("access-token");
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("refresh-token");

        Claims claims = mock(Claims.class);
        given(claims.getExpiration()).willReturn(Date.from(Instant.now().plusSeconds(3600)));
        given(claims.getIssuedAt()).willReturn(Date.from(Instant.now()));
        given(jwtTokenProvider.getClaims(anyString())).willReturn(claims);

        // when
        AuthResult result = oAuthLoginService.execute(userInfo);

        // then
        verify(createUserUseCase).execute(any(CreateUserCommand.class));
        assertThat(result.email()).isEqualTo(userInfo.email());
    }
}
