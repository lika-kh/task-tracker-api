package com.example.task_tracker_api.mapper;
import com.example.task_tracker_api.dto.project.ProjectCreateRequestDto;
import com.example.task_tracker_api.dto.project.ProjectResponseDto;
import com.example.task_tracker_api.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    ProjectResponseDto toResponseDto(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Project toEntity(ProjectCreateRequestDto request);
}

