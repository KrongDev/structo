package com.archbuilder.user.application.usecase;

import com.archbuilder.user.application.command.UpdateUserCommand;
import com.archbuilder.user.domain.model.User;

public interface UpdateUserUseCase {
    User execute(UpdateUserCommand command);
}
