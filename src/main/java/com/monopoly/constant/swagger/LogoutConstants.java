package com.monopoly.constant.swagger;

public class LogoutConstants {
    public static final String LOGOUT_REQUEST = """
        {
            "username": "john_doe"
        }
        """;

    public static final String LOGOUT_SUCCESS_RESPONSE = """
        {
            "success": true,
        }
        """;

    public static final String LOGOUT_BAD_REQUEST_RESPONSE = """
        {
            "timestamp": "2025-08-18T10:30:00.000+00:00",
            "status": 400,
            "error": "Bad Request",
            "message": "Username cannot be empty",
            "path": "/api/auth/login/logout"
        }
        """;

    public static final String LOGOUT_INTERNAL_ERROR_RESPONSE = """
        {
            "timestamp": "2025-08-18T10:30:00.000+00:00",
            "status": 500,
            "error": "Internal Server Error",
            "message": "An unexpected error occurred",
            "path": "/api/auth/login/logout"
        }
        """;
}
