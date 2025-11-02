package com.monopoly.service.lobby;

import com.monopoly.entity.Room;
import com.monopoly.entity.User;
import com.monopoly.repository.RoomRepository;
import com.monopoly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LobbyService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public void assignLobby(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Room> availableRoom = roomRepository.findFirstByStatus(Room.Status.NOT_STARTED);
            availableRoom.ifPresentOrElse(
                (Room room) -> addUserToRoom(user, room),
                () -> addUserToNewRoom(user)
            );
        }
    }

    private void addUserToRoom(User user, Room room)  {
        user.setRoomId(room.getRoomId());
        userRepository.save(user);
        roomRepository.save(room);
    }

    private void addUserToNewRoom(User user) {
        Room newRoom = Room.createNewRoom();
        addUserToRoom(user, newRoom);
    }
}

