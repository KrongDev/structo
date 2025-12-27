package com.archbuilder.auth.infra.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = extractEmail(attributes, registrationId);
        String name = extractName(attributes, registrationId);

        return new CustomOAuth2User(attributes, email, name, registrationId);
    }

    private String extractEmail(Map<String, Object> attributes, String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> (String) attributes.get("email");
            case "github" -> (String) attributes.get("email");
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth 제공자입니다: " + registrationId);
        };
    }

    private String extractName(Map<String, Object> attributes, String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> (String) attributes.get("name");
            case "github" -> (String) attributes.get("name");
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth 제공자입니다: " + registrationId);
        };
    }
}
