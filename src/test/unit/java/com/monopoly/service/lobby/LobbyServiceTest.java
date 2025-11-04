package com.monopoly.service.lobby;

import com.monopoly.controller.dto.RoomResponse;
import com.monopoly.entity.Room;
import com.monopoly.entity.User;
import com.monopoly.repository.RoomRepository;
import com.monopoly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LobbyServiceTest {
    @InjectMocks
    private LobbyService lobbyService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    private final String username = "testUser";
    private final Room room = Mockito.mock(Room.class);
    private final String roomId = "room123";

    @Test
    void assignLobby_whenWaitingRoomPresent() {
        User user = User.builder().username(username).build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(roomRepository.findFirstByStatus(Room.Status.NOT_STARTED.toString()))
                .thenReturn(Optional.of(room));
        when(room.getRoomId()).thenReturn(roomId);
        lobbyService.assignLobby(username);

        verify(userRepository).findByUsername(username);
        verify(roomRepository).findFirstByStatus(Room.Status.NOT_STARTED.toString());
        assertEquals(roomId, user.getRoomId());
        verify(userRepository).save(user);
        verify(roomRepository).save(room);
    }

    @Test
    void assignLobby_whenWaitingRoomNotPresent() {
        User user = User.builder().username(username).build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(roomRepository.findFirstByStatus(Room.Status.NOT_STARTED.toString()))
                .thenReturn(Optional.empty());
        lobbyService.assignLobby(username);

        verify(userRepository).findByUsername(username);
        verify(roomRepository).findFirstByStatus(Room.Status.NOT_STARTED.toString());
        verify(userRepository).save(user);
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomCaptor.capture());
        Room captoredRoom = roomCaptor.getValue();
        assertEquals(user.getRoomId(), captoredRoom.getRoomId());
    }

    @Test
    void assignLobby_whenCouldNotFindUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        lobbyService.assignLobby(username);

        verify(userRepository).findByUsername(username);
        verify(roomRepository, never()).findFirstByStatus(Room.Status.NOT_STARTED.toString());
        verify(userRepository, never()).save(any(User.class));
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void assignLobby_whenUserAlreadyInRoom() {
        User user = User.builder().username(username).roomId("existingRoom").build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        lobbyService.assignLobby(username);

        verify(userRepository).findByUsername(username);
        verify(roomRepository, never()).findFirstByStatus(Room.Status.NOT_STARTED.toString());
        verify(userRepository, never()).save(any(User.class));
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testGetLobbyStatus_success() {
        User user1 = User.builder().username("user1").roomId(roomId).build();
        User user2 = User.builder().username("user2").roomId(roomId).build();

        Room room = Room.builder()
                .roomId(roomId)
                .capacity(4)
                .currentPlayers(2)
                .build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userRepository.findAllByRoomId(roomId)).thenReturn(Arrays.asList(user1, user2));

        RoomResponse response = lobbyService.getLobbyStatus(roomId, username);

        assertNotNull(response);
        assertEquals(roomId, response.getRoomId());
        assertEquals(4, response.getCapacity());
        assertEquals(2, response.getCurrentPlayers());
        assertEquals(Arrays.asList("user1", "user2"), response.getPlayers());
    }

    @Test
    void testGetLobbyStatus_roomNotFound() {
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RoomResponse response = lobbyService.getLobbyStatus(roomId, username);

        assertNull(response);
    }
}
