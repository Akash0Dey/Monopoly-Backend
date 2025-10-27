package com.monopoly.controller.auth;

import com.monopoly.controller.dto.LogoutResponse;
import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.LoginService;
import com.monopoly.service.auth.LogoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private LoginService loginService;

    @Mock
    private LogoutService logoutService;

    @InjectMocks
    private AuthController loginController;

    @Test
    void testLogin_success() {
        UserRequest userRequest = new UserRequest("testUser");
        when(loginService.login(userRequest)).thenReturn(UserResponse.builder()
                .username("testUser")
                .build());
        UserResponse user = loginController.createUser(userRequest);

        assertEquals("testUser", user.getUsername());
        assertNull(user.getRoomId());
    }

    @Test
    void testLogout_success() {
        UserRequest userRequest = new UserRequest("testUser");
        when(logoutService.logout(userRequest)).thenReturn(true);
        LogoutResponse logoutResponse = loginController.logoutUser(userRequest);

        assertTrue(logoutResponse.isSuccess());
    }
}




