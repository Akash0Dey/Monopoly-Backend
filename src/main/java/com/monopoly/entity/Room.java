package com.monopoly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    private String roomId;

    @Builder.Default
    private String status = Status.NOT_STARTED.toString();

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private int capacity = 2;

    @Builder.Default
    private int currentPlayers = 0;

    public enum Status {
        NOT_STARTED,
        STARTED,
        ENDED
    }

    public static Room createNewRoom() {
        return Room.builder().roomId(UUID.randomUUID().toString()).build();
    }

    private boolean isFull() {
        return currentPlayers >= capacity;
    }

    private void startMatch() {
        this.status = Status.STARTED.toString();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean addPlayer() {
        if (isFull()) {
            return false;
        }
        currentPlayers++;
        this.updatedAt = LocalDateTime.now();
        if (isFull())   startMatch();
        return true;
    }
}

