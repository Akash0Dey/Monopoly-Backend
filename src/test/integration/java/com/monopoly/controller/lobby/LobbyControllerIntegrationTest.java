package com.monopoly.controller.lobby;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly.controller.auth.AuthController;
import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.service.auth.LoginService;
import com.monopoly.service.auth.LogoutService;
import com.monopoly.service.auth.UserService;
import com.monopoly.service.lobby.LobbyService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LobbyController.class)
public class LobbyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LobbyService lobbyService;

    @MockitoBean
    private UserService userService;

    private final String username = "testUser";

    @Test
    void testJoinLobby_success() throws Exception {
        doNothing().when(lobbyService).assignLobby(username);
        when(userService.findUser(username)).thenReturn(
                com.monopoly.entity.User.builder()
                        .username("testUser")
                        .roomId("room123")
                        .build()
        );

        mockMvc.perform(post("/api/lobby/join")
                .header("username", "testUser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.roomId").value("room123"));
    }
}
