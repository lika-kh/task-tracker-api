package com.example.task_tracker_api.dto.task;

import com.example.task_tracker_api.enums.TaskPriority;
import com.example.task_tracker_api.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        TaskPriority priority,
        Long projectId,
        Long assignedUserId,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {}
