package com.example.task_tracker_api.dto.auth;

public record RegisterRequestDto(
        String email,
        String password
) {}
