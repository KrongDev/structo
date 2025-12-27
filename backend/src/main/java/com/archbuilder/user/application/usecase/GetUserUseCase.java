package com.archbuilder.user.application.usecase;

import com.archbuilder.user.domain.model.User;

import java.util.Optional;

public interface GetUserUseCase {
    User getById(String id);
    Optional<User> findByEmail(String email);
}