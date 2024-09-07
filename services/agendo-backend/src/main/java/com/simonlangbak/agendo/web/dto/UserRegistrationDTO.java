package com.simonlangbak.agendo.web.dto;

import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.domain.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserRegistrationDTO(String username, String password, String email, UserRole role) {

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setEmail(this.email);
        user.setRole(this.role);
        return user;
    }
}
