package com.archbuilder.user.domain.model;

import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.PlanType;
import com.archbuilder.user.domain.model.vo.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("should create user successfully")
    void create() {
        // given
        String email = "test@example.com";
        String name = "Test User";
        AuthProvider provider = AuthProvider.GOOGLE;

        // when
        User user = User.createOAuth(
                email,
                name,
                provider,
                Set.of(Role.USER));

        // then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getProvider()).isEqualTo(provider);
        assertThat(user.getRoles()).contains(Role.USER);
        assertThat(user.getPlanType()).isEqualTo(PlanType.FREE);
    }

    @Test
    @DisplayName("should update profile")
    void updateProfile() {
        // given
        User user = User.create("test@a.com", "Old Name");

        // when
        user.updateProfile("New Name");

        // then
        assertThat(user.getName()).isEqualTo("New Name");
    }
}
