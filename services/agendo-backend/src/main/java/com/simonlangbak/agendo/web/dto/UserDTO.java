package com.simonlangbak.agendo.web.dto;

import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.domain.user.UserRole;

public record UserDTO(Long id, String username, UserRole userRole) {

    public static UserDTO fromDomain(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
