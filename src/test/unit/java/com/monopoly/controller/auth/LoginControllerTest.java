package com.monopoly.controller.auth;

import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LoginService loginService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LoginController loginController;

    @Test
    void testLogin() {
        UserRequest userRequest = new UserRequest("testUser");
        when(loginService.login(userRequest)).thenReturn(UserResponse.builder()
                .username("testUser")
                .build());
        UserResponse user = loginController.createUser(userRequest);

        assertEquals("testUser", user.getUsername());
        assertNull(user.getRoomId());
    }
}




