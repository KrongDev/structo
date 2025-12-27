package com.archbuilder.user.application.service;

import com.archbuilder.user.application.command.UpdateUserCommand;
import com.archbuilder.user.application.usecase.UpdateUserUseCase;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final GetUserService getUserService;
    
    @Override
    @Transactional
    public User execute(UpdateUserCommand command) {
        User user = getUserService.getById(command.userId());
        user.updateProfile(command.name());
        return userRepository.save(user);
    }
}