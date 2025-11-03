package com.monopoly.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoomTest {
    @Test
    void shouldCreateRoomWithRandomRoomIdAndDefaults() {
        Room room = Room.createNewRoom();

        assertNotNull(room.getRoomId());
        assertEquals(Room.Status.NOT_STARTED.toString(), room.getStatus());
        assertEquals(0, room.getCurrentPlayers());
        assertEquals(2, room.getCapacity());
    }

    @Test
    void shouldNotAddPlayerWhenRoomIsFull() {
        Room room = Room.builder()
                .roomId("room123")
                .status(Room.Status.NOT_STARTED.toString())
                .currentPlayers(2)
                .capacity(2)
                .build();

        boolean isPlayerAdded = room.addPlayer();

        assertEquals(2, room.getCurrentPlayers());
        assertFalse(isPlayerAdded);
    }

    @Test
    void shouldAddPlayerAndStartMatchWhenRoomBecomesFull() {
        Room room = Room.builder()
                .roomId("room123")
                .status(Room.Status.NOT_STARTED.toString())
                .currentPlayers(1)
                .capacity(2)
                .build();

        boolean isPlayerAdded = room.addPlayer();

        assertEquals(2, room.getCurrentPlayers());
        assertTrue(isPlayerAdded);
        assertEquals(Room.Status.STARTED.toString(), room.getStatus());
    }

    @Test
    void shouldAddPlayerWhenRoomIsNotFull() {
        Room room = Room.builder()
                .roomId("room123")
                .status(Room.Status.NOT_STARTED.toString())
                .currentPlayers(0)
                .capacity(2)
                .build();

        boolean isPlayerAdded = room.addPlayer();

        assertEquals(1, room.getCurrentPlayers());
        assertTrue(isPlayerAdded);
        assertEquals(Room.Status.NOT_STARTED.toString(), room.getStatus());
    }
}
