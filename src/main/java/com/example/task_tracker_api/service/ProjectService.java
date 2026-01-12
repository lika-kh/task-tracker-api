package com.example.task_tracker_api.service;

import com.example.task_tracker_api.dto.project.ProjectCreateRequestDto;
import com.example.task_tracker_api.dto.project.ProjectResponseDto;
import com.example.task_tracker_api.dto.project.ProjectUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProjectService {
    ProjectResponseDto createProject(ProjectCreateRequestDto request);

    ProjectResponseDto updateProject(Long projectId, ProjectUpdateRequestDto request);

    void deleteProject(Long projectId);

    Page<ProjectResponseDto> getProjects(Pageable pageable);
}