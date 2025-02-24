package com.jhh.core.domain.user.service;

import com.jhh.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isUserExists(Integer userId) {
        return userRepository.existsById(userId);
    }
}
