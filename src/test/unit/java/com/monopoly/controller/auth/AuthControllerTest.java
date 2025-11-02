package com.monopoly.controller.auth;

import com.monopoly.controller.dto.LogoutResponse;
import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.LoginService;
import com.monopoly.service.auth.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    private final String username = "testUser";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);


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
        when(request.getHeader("username")).thenReturn("testUser");
        when(logoutService.logout(username)).thenReturn(true);
        LogoutResponse logoutResponse = loginController.logoutUser(request);

        assertTrue(logoutResponse.isSuccess());
    }
}




