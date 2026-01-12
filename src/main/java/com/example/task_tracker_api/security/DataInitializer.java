
package com.example.task_tracker_api.security;

import com.example.task_tracker_api.entity.User;
import com.example.task_tracker_api.enums.RoleType;
import com.example.task_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {

            if (userRepository.findByEmail("admin@test.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@test.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(RoleType.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("manager@test.com").isEmpty()) {
                User manager = new User();
                manager.setEmail("manager@test.com");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRole(RoleType.MANAGER);
                userRepository.save(manager);
            }

            if (userRepository.findByEmail("user@test.com").isEmpty()) {
                User user = new User();
                user.setEmail("user@test.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole(RoleType.USER);
                userRepository.save(user);
            }
        };
    }
}
