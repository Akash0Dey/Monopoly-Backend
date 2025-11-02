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

    private final String username = "testUser";

    @Test
    void Logout_success() {
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.deleteByUsername(username)).thenReturn(1);

        assertTrue(logoutService.logout(username));
    }

    @Test
    void Logout_UserNotExit() {
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertFalse(logoutService.logout(username));
    }

    @Test
    void  Logout_UserExitButDeleteNotDone() {
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.deleteByUsername(username)).thenReturn(0);

        assertFalse(logoutService.logout(username));
    }
}
