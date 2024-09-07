package com.simonlangbak.agendo.loginservice.web.dto;

import com.simonlangbak.agendo.loginservice.domain.User;
import com.simonlangbak.agendo.loginservice.domain.UserRole;
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
