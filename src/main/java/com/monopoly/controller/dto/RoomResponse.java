package com.monopoly.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomResponse {
    private String roomId;
    private int currentPlayers;
    private int capacity;
    private List<String> players;
}

