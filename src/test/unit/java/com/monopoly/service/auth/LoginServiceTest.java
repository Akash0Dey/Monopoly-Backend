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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private  LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Test
    void Login_success() {
        UserRequest userRequest = new UserRequest("testUser");
        when(userRepository.save(any())).thenReturn(User.builder().username(userRequest.getUsername()).build());
        UserResponse userResponse = loginService.login(userRequest);

        assertEquals("testUser", userResponse.getUsername());
    }
}
