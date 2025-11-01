package com.monopoly.service.auth;

import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.entity.User;
import com.monopoly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {
    @InjectMocks
    private  LogoutService logoutService;

    @Mock
    private UserRepository userRepository;

    @Test
    void Logout_success() {
        UserRequest userRequest = new UserRequest("testUser");
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(true);
        when(userRepository.deleteByUsername(userRequest.getUsername())).thenReturn(1);

        assertTrue(logoutService.logout(userRequest));
    }

    @Test
    void Logout_UserNotExit() {
        UserRequest userRequest = new UserRequest("testUser");
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);

        assertFalse(logoutService.logout(userRequest));
    }
}
