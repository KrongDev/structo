package com.archbuilder.user.interfaces.controller;

import com.archbuilder.user.application.command.UpdateUserCommand;
import com.archbuilder.user.application.usecase.GetUserUseCase;
import com.archbuilder.user.application.usecase.UpdateUserUseCase;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.interfaces.controller.dto.UpdateUserRequest;
import com.archbuilder.user.interfaces.controller.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal String userId) {
        User user = getUserUseCase.getById(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            @AuthenticationPrincipal String userId,
            @RequestBody UpdateUserRequest request) {

        UpdateUserCommand command = new UpdateUserCommand(userId, request.name());
        User user = updateUserUseCase.execute(command);
        return ResponseEntity.ok(UserResponse.from(user));
    }

}
