package com.example.task_tracker_api.service.impl;
import com.example.task_tracker_api.dto.auth.AuthResponseDto;
import com.example.task_tracker_api.dto.auth.LoginRequestDto;
import com.example.task_tracker_api.dto.auth.RegisterRequestDto;
import com.example.task_tracker_api.entity.User;
import com.example.task_tracker_api.enums.RoleType;
import com.example.task_tracker_api.exception.EmailAlreadyExistsException;
import com.example.task_tracker_api.exception.UserNotFoundException;
import com.example.task_tracker_api.repository.UserRepository;
import com.example.task_tracker_api.security.JwtService;
import com.example.task_tracker_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto registration(RegisterRequestDto request) {

        log.info("Registration request received for email={}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(RoleType.USER)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());
        log.info("User registered successfully: userId={}", savedUser.getId());
        return new AuthResponseDto(token);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        log.info("Login request received for email={}", request.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        String token = jwtService.generateToken(user.getEmail());
        log.info("Login successful: userId={}", user.getId());
        return new AuthResponseDto(token);
    }
}
