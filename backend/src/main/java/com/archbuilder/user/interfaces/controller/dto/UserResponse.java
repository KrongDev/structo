package com.archbuilder.user.interfaces.controller.dto;

import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
    String id,
    String email,
    String name,
    Set<Role> roles,
    AuthProvider provider,
    LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRoles(),
            user.getProvider(),
            user.getCreatedAt()
        );
    }
}