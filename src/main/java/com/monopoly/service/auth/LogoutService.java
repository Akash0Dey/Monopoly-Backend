package com.monopoly.service.auth;

import com.monopoly.controller.dto.UserRequest;
import com.monopoly.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LogoutService {
    private final UserRepository userRepository;

    public LogoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean logout(String username) {
        if (!userRepository.existsByUsername(username)) {
            log.info("user with username {} not exist", username);
            return false;
        }

        log.info("user with username {} is being deleted", username);
        return 0 < userRepository.deleteByUsername(username);
    }
}
