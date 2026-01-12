package com.example.task_tracker_api.controller;
import com.example.task_tracker_api.dto.project.ProjectCreateRequestDto;
import com.example.task_tracker_api.dto.project.ProjectResponseDto;
import com.example.task_tracker_api.dto.project.ProjectUpdateRequestDto;
import com.example.task_tracker_api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @RequestBody ProjectCreateRequestDto request
    ) {
        ProjectResponseDto response =
                projectService.createProject(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectUpdateRequestDto request
    ) {
        ProjectResponseDto response =
                projectService.updateProject(projectId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProjectResponseDto>> getProjects(
            Pageable pageable
    ) {
        Page<ProjectResponseDto> page =
                projectService.getProjects(pageable);
        return ResponseEntity.ok(page);
    }
}
