package com.archbuilder.user.domain.service;

import com.archbuilder.user.domain.exception.DuplicateEmailException;
import com.archbuilder.user.domain.exception.OAuthUserAlreadyExistsException;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.model.vo.Role;
import com.archbuilder.user.domain.repository.UserRepository;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(
            String email,
            String name,
            AuthProvider provider,
            Set<Role> roles
    ) {

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        return User.createOAuth(email, name, provider, roles);
    }
}
