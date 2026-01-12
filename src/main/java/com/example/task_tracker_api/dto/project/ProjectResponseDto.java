package com.example.task_tracker_api.dto.project;
import java.time.LocalDateTime;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        Long ownerId,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {}
