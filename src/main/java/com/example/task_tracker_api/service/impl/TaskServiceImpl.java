package com.example.task_tracker_api.service.impl;
import com.example.task_tracker_api.dto.task.TaskCreateRequestDto;
import com.example.task_tracker_api.dto.task.TaskResponseDto;
import com.example.task_tracker_api.dto.task.TaskUpdateRequestDto;
import com.example.task_tracker_api.entity.Project;
import com.example.task_tracker_api.entity.Task;
import com.example.task_tracker_api.entity.User;
import com.example.task_tracker_api.enums.RoleType;
import com.example.task_tracker_api.enums.TaskStatus;
import com.example.task_tracker_api.exception.AccessDeniedException;
import com.example.task_tracker_api.mapper.TaskMapper;
import com.example.task_tracker_api.repository.ProjectRepository;
import com.example.task_tracker_api.repository.TaskRepository;
import com.example.task_tracker_api.repository.UserRepository;
import com.example.task_tracker_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDto createTask(TaskCreateRequestDto request) {

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User assignedUser = userRepository.findById(request.assignedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .priority(request.priority())
                .status(TaskStatus.TODO)
                .project(project)
                .assignedUser(assignedUser)
                .build();

        log.info("Creating task: title='{}', projectId={}, assignedUserId={}",
                task.getTitle(), project.getId(), assignedUser.getId());

        return taskMapper.toResponseDto(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto updateTask(Long taskId, TaskUpdateRequestDto request) {
        Task task = getTaskOrThrow(taskId);
        checkTaskAccess(task);
        checkAssignPermission();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setPriority(request.priority());

        log.info("Updating task: taskId={}", taskId);

        return taskMapper.toResponseDto(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto updateTaskStatus(Long taskId, TaskStatus status) {

        Task task = getTaskOrThrow(taskId);
        checkTaskAccess(task);
        task.setStatus(status);

        log.info("Updating task status: taskId={}, status={}", taskId, status);

        return taskMapper.toResponseDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = getTaskOrThrow(taskId);
        checkTaskAccess(task);
        log.info("Deleting task: taskId={}", taskId);
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponseDto> getTasksByProject(Long projectId, Pageable pageable) {

        log.info("Fetching tasks by project: projectId={}", projectId);

        return taskRepository.findByProject_Id(projectId, pageable)
                .map(taskMapper::toResponseDto);
    }


    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    private void checkTaskAccess(Task task) {
        Long currentUserId = getCurrentUserId();

        boolean isAssignedUser =
                task.getAssignedUser().getId().equals(currentUserId);

        boolean isProjectOwner =
                task.getProject().getOwner().getId().equals(currentUserId);

        if (!isAssignedUser && !isProjectOwner) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private void checkAssignPermission() {
        Long currentUserId = getCurrentUserId();

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getRole() != RoleType.ADMIN &&
                currentUser.getRole() != RoleType.MANAGER) {
            throw new RuntimeException("Only ADMIN or MANAGER can assign tasks");
        }
    }


    private Long getCurrentUserId() {
        return 1L;
    }
}
