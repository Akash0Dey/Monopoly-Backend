package com.monopoly.controller.lobby;

import com.monopoly.controller.dto.RoomResponse;
import com.monopoly.controller.dto.UserRequest;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.entity.Room;
import com.monopoly.entity.User;
import com.monopoly.repository.UserRepository;
import com.monopoly.service.auth.UserService;
import com.monopoly.service.lobby.LobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/lobby")
public class LobbyController {

    private final LobbyService lobbyService;

    private final UserService userService;

    @PostMapping("/join")
    @Operation(
            summary = "Auto-join available lobby",
            description = "Automatically assigns a player to an available lobby or creates a new one if all are full"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully joined lobby",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            )
    })
    public UserResponse joinLobby(HttpServletRequest request) {
        String username = request.getHeader("username");
        log.info("User {} requesting to join lobby", username);
        lobbyService.assignLobby(username);
        User user = userService.findUser(username);

        return UserResponse.builder()
                .username(user.getUsername())
                .roomId(user.getRoomId())
                .build();
    }
}
