package com.example.task_tracker_api.dto.task;

import com.example.task_tracker_api.enums.TaskPriority;
import java.time.LocalDate;


public record TaskCreateRequestDto(
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        Long projectId,
        Long assignedUserId
) {}
