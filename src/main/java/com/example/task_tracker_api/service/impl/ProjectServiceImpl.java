package com.example.task_tracker_api.service.impl;
import com.example.task_tracker_api.dto.project.ProjectCreateRequestDto;
import com.example.task_tracker_api.dto.project.ProjectResponseDto;
import com.example.task_tracker_api.dto.project.ProjectUpdateRequestDto;
import com.example.task_tracker_api.entity.Project;
import com.example.task_tracker_api.entity.User;
import com.example.task_tracker_api.enums.RoleType;
import com.example.task_tracker_api.exception.UserNotFoundException;
import com.example.task_tracker_api.mapper.ProjectMapper;
import com.example.task_tracker_api.repository.ProjectRepository;
import com.example.task_tracker_api.repository.UserRepository;
import com.example.task_tracker_api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponseDto createProject(ProjectCreateRequestDto request) {

        User currentUser = getActiveUser();
        log.info("Create project request by userId={}, role={}",
                currentUser.getId(), currentUser.getRole());

        assertAdminOrManager(currentUser);
        Project project = projectMapper.toEntity(request);
        project.setOwner(currentUser);
        Project saved = projectRepository.save(project);

        log.info("Project created: projectId={}, ownerId={}",
                saved.getId(), currentUser.getId());

        return projectMapper.toResponseDto(saved);
    }



    @Override
    @Transactional
    public ProjectResponseDto updateProject(
            Long projectId,
            ProjectUpdateRequestDto request
    ) {
        User currentUser = getActiveUser();
        log.info("Update project request: projectId={}, userId={}",
                projectId, currentUser.getId());

        Project project = getProjectOrThrow(projectId);
        assertAdminOrManager(currentUser);
        project.setName(request.name());
        project.setDescription(request.description());
        Project updated = projectRepository.save(project);
        log.info("Project updated: projectId={}", projectId);
        return projectMapper.toResponseDto(updated);
    }


    @Override
    @Transactional
    public void deleteProject(Long projectId) {

        User currentUser = getActiveUser();
        log.info("Delete project request: projectId={}, userId={}",
                projectId, currentUser.getId());

        Project project = getProjectOrThrow(projectId);
        assertAdminOrManager(currentUser);
        projectRepository.delete(project);

        log.info("Project deleted: projectId={}", projectId);
    }


    @Override
    public Page<ProjectResponseDto> getProjects(Pageable pageable) {

        User currentUser = getActiveUser();
        log.info("Get projects request by userId={}, role={}",
                currentUser.getId(), currentUser.getRole());

        Page<Project> page;

        if (currentUser.getRole() == RoleType.ADMIN) {
            page = projectRepository.findAll(pageable);
        } else if (currentUser.getRole() == RoleType.MANAGER) {
            page = projectRepository.findByOwner_Id(
                    currentUser.getId(), pageable);
        } else {
            throw new AccessDeniedException("USER has no access to projects");
        }

        return page.map(projectMapper::toResponseDto);
    }


    private User getActiveUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private void assertAdminOrManager(User user) {
        if (user.getRole() != RoleType.ADMIN &&
                user.getRole() != RoleType.MANAGER) {
            throw new AccessDeniedException(
                    "Only ADMIN or MANAGER allowed to manage projects"
            );
        }
    }


    private Project getProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));
    }
}
