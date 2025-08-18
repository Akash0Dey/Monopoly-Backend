package com.monopoly.constant.swagger;

public class LoginConstants {
    public static final String LOGIN_REQUEST = """
            {
                "username": "john_doe"
            }
            """;

    public static final String LOGIN_SUCCESS_RESPONSE = """
            {
                "username": "john_doe",
                "roomId": "room_123456"
            }
            """;

    public static final String LOGIN_BAD_REQUEST_RESPONSE = """
            {
                "timestamp": "2025-08-18T10:30:00.000+00:00",
                "status": 400,
                "error": "Bad Request",
                "message": "Username cannot be empty",
                "path": "/api/auth/login"
            }
            """;

    public static final String LOGIN_INTERNAL_ERROR_RESPONSE = """
            {
                "timestamp": "2025-08-18T10:30:00.000+00:00",
                "status": 500,
                "error": "Internal Server Error",
                "message": "An unexpected error occurred",
                "path": "/api/auth/login"
            }
            """;
}
