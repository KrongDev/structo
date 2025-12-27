package com.archbuilder.user.application.service;

import com.archbuilder.user.application.command.CreateUserCommand;
import com.archbuilder.user.application.usecase.CreateUserUseCase;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.repository.UserRepository;
import com.archbuilder.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User execute(CreateUserCommand command) {
        User user = userService.createUser(
                command.email(),
                command.name(),
                command.provider(),
                command.roles()
        );
        return userRepository.save(user);
    }
}
