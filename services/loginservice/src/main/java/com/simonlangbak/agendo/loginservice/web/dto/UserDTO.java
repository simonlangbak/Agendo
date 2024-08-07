package com.simonlangbak.agendo.loginservice.web.dto;

import com.simonlangbak.agendo.loginservice.domain.User;
import com.simonlangbak.agendo.loginservice.domain.UserRole;

public record UserDTO(Long id, String username, UserRole userRole) {

    public static UserDTO fromDomain(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
