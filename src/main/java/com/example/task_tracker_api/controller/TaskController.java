package com.example.task_tracker_api.controller;
import com.example.task_tracker_api.dto.task.TaskCreateRequestDto;
import com.example.task_tracker_api.dto.task.TaskResponseDto;
import com.example.task_tracker_api.dto.task.TaskUpdateRequestDto;
import com.example.task_tracker_api.enums.TaskStatus;
import com.example.task_tracker_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @RequestBody TaskCreateRequestDto request
    ) {
        TaskResponseDto response =
                taskService.createTask(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskUpdateRequestDto request
    ) {
        TaskResponseDto response =
                taskService.updateTask(taskId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status
    ) {

        TaskResponseDto response =
                taskService.updateTaskStatus(taskId, status);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId
    ) {

        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }


}
