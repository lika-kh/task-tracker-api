package com.example.task_tracker_api.repository;

import com.example.task_tracker_api.entity.Task;
import com.example.task_tracker_api.enums.TaskPriority;
import com.example.task_tracker_api.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByProject_Id(Long projectId, Pageable pageable);

    Page<Task> findByAssignedUser_Id(Long userId, Pageable pageable);

    Page<Task> findByAssignedUser_IdAndStatus(
            Long userId,
            TaskStatus status,
            Pageable pageable
    );

    Page<Task> findByAssignedUser_IdAndPriority(
            Long userId,
            TaskPriority priority,
            Pageable pageable
    );
}
