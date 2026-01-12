package com.example.task_tracker_api.mapper;


import com.example.task_tracker_api.dto.auth.RegisterRequestDto;
import com.example.task_tracker_api.dto.user.UserResponseDto;
import com.example.task_tracker_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponseDto(User user);
}

