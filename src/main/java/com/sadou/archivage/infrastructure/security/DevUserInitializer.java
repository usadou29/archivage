package com.sadou.archivage.infrastructure.security;

import com.sadou.archivage.application.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;

@Configuration
public class DevUserInitializer {
    @Bean
    public CommandLineRunner initUsers(UserService userService) {
        return args -> {
            if (userService.findByEmail("admin@local") == null) {
                userService.registerUser(
                        "admin@local",
                        "Admin",
                        "adminpass",
                        Set.of("ADMIN", "EDITOR")
                );
            }
        };
    }
}