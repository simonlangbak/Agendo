package com.simonlangbak.agendo.loginservice.web;

import com.simonlangbak.agendo.loginservice.domain.User;
import com.simonlangbak.agendo.loginservice.service.UserService;
import com.simonlangbak.agendo.loginservice.web.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(UserDTO::fromDomain).toList();
    }

    @GetMapping("/me")
    public UserDTO getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken
                && authentication.getPrincipal() instanceof User authenticatedUser) {
            return UserDTO.fromDomain(authenticatedUser);
        }
        throw new UsernameNotFoundException("No authenticated user found");
    }
}
