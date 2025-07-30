package com.sadou.archivage.application;

import com.sadou.archivage.domain.entity.User;
import com.sadou.archivage.infrastructure.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.userRepository = repo;
        this.passwordEncoder = encoder;
    }

    public User registerUser(String email, String username, String rawPassword, Set<String> roles) {
        String hash = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(hash);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}