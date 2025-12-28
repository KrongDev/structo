package com.archbuilder.auth.infra.oauth2;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.dto.OAuthUserInfo;
import com.archbuilder.auth.application.usecase.OAuthLoginUseCase;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        log.info("OAuth2 인증 성공: email={}, provider={}",
                oAuth2User.getEmail(), oAuth2User.getProvider());

        OAuthUserInfo userInfo = new OAuthUserInfo(
                oAuth2User.getEmail(),
                oAuth2User.getName(),
                AuthProvider.valueOf(oAuth2User.getProvider().toUpperCase())
        );

        AuthResult authResult = oAuthLoginUseCase.execute(userInfo);

        // JSON 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authResult));
    }
}
