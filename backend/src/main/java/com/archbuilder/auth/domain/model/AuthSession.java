package com.archbuilder.auth.domain.model;

import com.archbuilder.user.domain.model.vo.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class AuthSession {
    private String userId;
    private String email;
    private String name;
    private Set<Role> roles;
    private String accessToken;
    private String refreshToken;
}