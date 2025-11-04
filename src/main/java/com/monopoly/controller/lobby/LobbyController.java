package com.monopoly.controller.lobby;

import com.monopoly.constant.swagger.LobbyConstants;
import com.monopoly.controller.dto.RoomResponse;
import com.monopoly.controller.dto.UserResponse;
import com.monopoly.entity.Room;
import com.monopoly.entity.User;
import com.monopoly.service.auth.UserService;
import com.monopoly.service.lobby.LobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.monopoly.constant.swagger.LobbyConstants.JOIN_LOBBY_RESPONSE;

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
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(
                                    name = "Join Lobby Response Example",
                                    description = "Example response after joining a lobby",
                                    value = LobbyConstants.JOIN_LOBBY_RESPONSE
                            )
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

    @GetMapping("/status")
    @Operation(
            summary = "Get lobby status",
            description = "Retrieves the current status of all lobbies including room details and player counts"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved lobby status",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoomResponse.class),
                            examples =  @ExampleObject(
                                    name = "Lobby Status Response Example",
                                    description = "Example response showing the status of all lobbies",
                                    value = LobbyConstants.STATIC_LOBBY_RESPONSE
                            )
                    )
            )
    })
    public RoomResponse getLobbyStatus(HttpServletRequest request) {
        String username = request.getHeader("username");
        String roomId = request.getHeader("roomId");
        log.info("User {} requesting lobby status for room {}", username, roomId);
        RoomResponse roomResponse = lobbyService.getLobbyStatus(roomId, username);
        log.info("Lobby {} can have {}, has {} players", roomResponse.getRoomId(),
                roomResponse.getCapacity(), roomResponse.getCurrentPlayers());
        return roomResponse;
    }
}
