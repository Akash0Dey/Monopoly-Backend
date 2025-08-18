package com.monopoly.service.auth;

import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.entity.User;
import com.monopoly.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponse login(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .build();
        User savedUser = userRepository.save(user);
        return UserResponse.builder()
                .username(savedUser.getUsername())
                .roomId(savedUser.getRoomId())
                .build();
    }
}
