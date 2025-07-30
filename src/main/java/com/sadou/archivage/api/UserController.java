package com.sadou.archivage.api;

import com.sadou.archivage.domain.entity.User;
import com.sadou.archivage.infrastructure.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Exemple: accessible seulement aux ADMIN et EDITOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    @GetMapping("/by-email")
    public User findByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
