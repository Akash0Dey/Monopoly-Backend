package com.monopoly.service.lobby;

import com.monopoly.controller.dto.RoomResponse;
import com.monopoly.entity.Room;
import com.monopoly.entity.User;
import com.monopoly.repository.RoomRepository;
import com.monopoly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LobbyService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public void assignLobby(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() && optionalUser.get().getRoomId() == null) {
            User user = optionalUser.get();
            Optional<Room> availableRoom = roomRepository.findFirstByStatus(Room.Status.NOT_STARTED.toString());
            availableRoom.ifPresentOrElse(
                (Room room) -> addUserToRoom(user, room),
                () -> addUserToNewRoom(user)
            );
        }
    }

    private void addUserToRoom(User user, Room room)  {
        boolean isPlayerAdded = room.addPlayer();
        user.setRoomId(room.getRoomId());

        log.info("Adding user {} to room {}: {}", user.getUsername(), room.getRoomId(), isPlayerAdded);

        userRepository.save(user);
        roomRepository.save(room);
    }

    private void addUserToNewRoom(User user) {
        Room newRoom = Room.createNewRoom();
        addUserToRoom(user, newRoom);
    }

    public RoomResponse getLobbyStatus(String roomId, String username) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return null;
        }
        Room room = optionalRoom.get();
        List<User> players = userRepository.findAllByRoomId(roomId);
        List<String> playersName = players.stream().map(User::getUsername).toList();
        log.info("players in room {}: {}", roomId, playersName);
        return RoomResponse.builder()
                .roomId(roomId)
                .capacity(room.getCapacity())
                .currentPlayers(room.getCurrentPlayers())
                .players(playersName)
                .build();
    }
}

