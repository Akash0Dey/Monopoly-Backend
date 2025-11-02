package com.monopoly.service.auth;

import com.monopoly.entity.User;
import com.monopoly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static final String username = "testUser";

    @Test
    void testFindUser_success() {
        when(userRepository.findByUsername(username)).thenReturn(
                Optional.of(com.monopoly.entity.User.builder()
                        .username(username)
                        .roomId("room123")
                        .build())
        );

        User user = userService.findUser(username);

        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("room123", user.getRoomId());
    }

    @Test
    void testFindUser_notFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> {
            userService.findUser(username);
        });
    }
}
