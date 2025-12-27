package com.archbuilder.user.application.command;

import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.Role;

import java.util.Set;

public record CreateUserCommand(
        String email,
        String name,
        AuthProvider provider,
        Set<Role> roles
) {
    public CreateUserCommand(String email, String name, AuthProvider provider) {
        this(email, name, provider, Set.of(Role.USER));
    }
}