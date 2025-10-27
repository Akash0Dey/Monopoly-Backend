package com.monopoly.service.auth;

import com.monopoly.controller.dto.UserRequest;
import com.monopoly.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final UserRepository userRepository;

    public LogoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean logout(UserRequest userRequest) {
        if (!userRepository.existsByUsername(userRequest.getUsername())) {
            return false;
        }

        return userRepository.deleteByUsername(userRequest.getUsername());
    }
}
