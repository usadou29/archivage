package com.sadou.archivage.application;

import com.sadou.archivage.domain.User;
import com.sadou.archivage.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void registerUserHashesPasswordAndSavesUser() {
        // Arrange
        var userRepository = mock(UserRepository.class);
        var passwordEncoder = mock(PasswordEncoder.class);
        var userService = new UserService(userRepository, passwordEncoder);

        String email = "test@example.com";
        String username = "Test";
        String rawPassword = "myPass";
        String hash = "hashé";
        Set<String> roles = Set.of("EDITOR");

        when(passwordEncoder.encode(rawPassword)).thenReturn(hash);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        User result = userService.registerUser(email, username, rawPassword, roles);

        // Assert
        assertThat(result).isNotNull();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isEqualTo(hash);
        assertThat(savedUser.getRoles()).contains("EDITOR");
    }
}
