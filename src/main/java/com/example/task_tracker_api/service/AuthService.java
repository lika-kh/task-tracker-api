package com.example.task_tracker_api.service;

import com.example.task_tracker_api.dto.auth.AuthResponseDto;
import com.example.task_tracker_api.dto.auth.LoginRequestDto;
import com.example.task_tracker_api.dto.auth.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto registration(RegisterRequestDto requestDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);
}