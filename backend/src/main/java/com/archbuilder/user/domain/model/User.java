package com.archbuilder.user.domain.model;

import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.PlanType;
import com.archbuilder.user.domain.model.vo.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String email;
    private String name;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    private AuthProvider provider;
    private PlanType planType;
    private LocalDateTime createdAt;

    public static User create(
        String email,
        String name
    ) {
        return User.builder()
                .provider(AuthProvider.LOCAL)
                .email(email)
                .name(name)
                .planType(PlanType.FREE)
                .build();
    }

    public static User createOAuth(
        String email,
        String name,
        AuthProvider provider,
        Set<Role> roles
    ) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(provider)
                .roles(roles)
                .planType(PlanType.FREE)
                .build();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    public void updateProfile(String name) {
        this.name = name;
    }
}
