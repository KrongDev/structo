package com.archbuilder.user.application.service;

import com.archbuilder.user.application.command.CreateUserCommand;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.Role;
import com.archbuilder.user.domain.repository.UserRepository;
import com.archbuilder.user.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @InjectMocks
    private CreateUserService createUserService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("should create and save user")
    void execute() {
        // given
        CreateUserCommand command = new CreateUserCommand(
                "test@example.com",
                "Test User",
                AuthProvider.GOOGLE,
                Set.of(Role.USER));

        // Use factory method since builder is private
        User user = User.createOAuth(
                command.email(),
                command.name(),
                command.provider(),
                command.roles());

        given(userService.createUser(
                eq(command.email()),
                eq(command.name()),
                eq(command.provider()),
                any(Set.class))).willReturn(user);

        given(userRepository.save(user)).willReturn(user);

        // when
        User result = createUserService.execute(command);

        // then
        assertThat(result).isEqualTo(user);
        verify(userService).createUser(eq(command.email()), any(), any(), any());
        verify(userRepository).save(user);
    }
}
