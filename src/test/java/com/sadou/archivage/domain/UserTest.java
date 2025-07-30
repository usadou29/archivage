package com.sadou.archivage.domain;

import com.sadou.archivage.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    void isAdminReturnsTrueIfUserHasAdminRole() {
        User user = new User();
        user.setRoles(Set.of("ADMIN", "EDITOR"));
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    void isAdminReturnsFalseIfUserHasNoAdminRole() {
        User user = new User();
        user.setRoles(Set.of("EDITOR"));
        assertThat(user.isAdmin()).isFalse();
    }
}
