package com.example.task_tracker_api.dto.user;
import com.example.task_tracker_api.enums.RoleType;

public record UserResponseDto(
        Long id,
        String email,
        RoleType role
) {}
