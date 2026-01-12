package com.example.task_tracker_api.controller;
import com.example.task_tracker_api.dto.auth.AuthResponseDto;
import com.example.task_tracker_api.dto.auth.LoginRequestDto;
import com.example.task_tracker_api.dto.auth.RegisterRequestDto;
import com.example.task_tracker_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        return authService.registration(request);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
}
