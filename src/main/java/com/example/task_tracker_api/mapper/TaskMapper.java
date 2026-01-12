package com.example.task_tracker_api.mapper;


import com.example.task_tracker_api.dto.task.TaskCreateRequestDto;
import com.example.task_tracker_api.dto.task.TaskResponseDto;
import com.example.task_tracker_api.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assignedUserId", source = "assignedUser.id")
    TaskResponseDto toResponseDto(Task task);
}

