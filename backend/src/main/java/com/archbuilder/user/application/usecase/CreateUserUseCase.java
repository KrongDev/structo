package com.archbuilder.user.application.usecase;

import com.archbuilder.user.application.command.CreateUserCommand;
import com.archbuilder.user.domain.model.User;

public interface CreateUserUseCase {
    User execute(CreateUserCommand command);
}
