package com.example.task_tracker_api.dto.auth;
public record LoginRequestDto(
        String email,
        String password
) {}
