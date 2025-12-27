package com.archbuilder.user.application.service;

import com.archbuilder.user.application.usecase.GetUserUseCase;
import com.archbuilder.user.domain.exception.UserNotFoundException;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService implements GetUserUseCase {
    private final UserRepository userRepository;

    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
