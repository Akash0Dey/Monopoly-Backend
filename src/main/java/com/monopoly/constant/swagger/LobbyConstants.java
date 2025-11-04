package com.monopoly.constant.swagger;

public class LobbyConstants {
    public static final String JOIN_LOBBY_RESPONSE = """
    {
    "username": "player1",
    "roomId": 123
    }""";

    public  static final String STATIC_LOBBY_RESPONSE = """
    {
        "roomId": 101,
        "currentPlayers": 3,
        "maxPlayers": 6,
        "status": "WAITING"
    }
    """;
}
