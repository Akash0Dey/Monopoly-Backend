package com.monopoly.controller.lobby;
import com.monopoly.controller.dto.RoomResponse;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.UserService;
import com.monopoly.service.lobby.LobbyService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        verify(lobbyService).assignLobby(username);
        verify(userService).findUser(username);
        assertEquals("testUser", user.getUsername());
        assertEquals("room123", user.getRoomId());
    }

    @Test
    void testLobbyStatus() {
        String roomId = "room123";
        when(request.getHeader("username")).thenReturn(username);
        when(request.getHeader("roomId")).thenReturn(roomId);
        when(lobbyService.getLobbyStatus(roomId, username)).thenReturn(
                RoomResponse.builder()
                        .roomId(roomId)
                        .currentPlayers(4)
                        .capacity(4)
                        .players(Arrays.asList("user1", "user2", "user3", "user4"))
                        .build()
        );

        RoomResponse response = lobbyController.getLobbyStatus(request);

        verify(lobbyService).getLobbyStatus(roomId, username);
        assertNotNull(response);
        assertEquals(roomId, response.getRoomId());
        assertEquals(4, response.getCurrentPlayers());
        assertEquals(4, response.getCapacity());
        assertEquals(java.util.Arrays.asList("user1", "user2", "user3", "user4"), response.getPlayers());
    }
}
