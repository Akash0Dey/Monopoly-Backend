package com.monopoly.repository;

import com.monopoly.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Transactional
    Optional<Room> findFirstByStatus(String status);
}

