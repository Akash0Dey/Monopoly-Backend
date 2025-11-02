package com.monopoly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    private Status status = Status.NOT_STARTED;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private static int CAPACITY = 2;

    public enum Status {
        NOT_STARTED,
        STARTED,
        ENDED
    }

    public static Room createNewRoom() {
        return Room.builder().roomId(UUID.randomUUID().toString()).build();
    }
}

