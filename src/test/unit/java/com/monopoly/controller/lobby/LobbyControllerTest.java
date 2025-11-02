package com.monopoly.controller.lobby;
import com.monopoly.controller.dto.LogoutResponse;
import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.LoginService;
import com.monopoly.service.auth.LogoutService;
import com.monopoly.service.auth.UserService;
import com.monopoly.service.lobby.LobbyService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LobbyControllerTest {
    @Mock
    private LobbyService lobbyService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LobbyController lobbyController;

    private final String username = "testUser";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void testJoinLobby_success() {
        when(request.getHeader("username")).thenReturn(username);
        doNothing().when(lobbyService).assignLobby(username);
        when(userService.findUser(username)).thenReturn(
                com.monopoly.entity.User.builder()
                        .username("testUser")
                        .roomId("room123")
                        .build()
        );

        UserResponse user = lobbyController.joinLobby(request);

        assertEquals("testUser", user.getUsername());
        assertEquals("room123", user.getRoomId());
    }
}
