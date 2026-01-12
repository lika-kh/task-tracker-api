package com.example.task_tracker_api.service;

import com.example.task_tracker_api.dto.task.TaskCreateRequestDto;
import com.example.task_tracker_api.dto.task.TaskResponseDto;
import com.example.task_tracker_api.dto.task.TaskUpdateRequestDto;
import com.example.task_tracker_api.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto createTask(TaskCreateRequestDto request);

    TaskResponseDto updateTask(Long taskId, TaskUpdateRequestDto request);

    void deleteTask(Long taskId);


    TaskResponseDto updateTaskStatus(Long taskId, TaskStatus status);

    Page<TaskResponseDto> getTasksByProject(
            Long projectId,
            Pageable pageable
    );

}
