package com.simonlangbak.agendo.service;

import com.simonlangbak.agendo.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getByUsername(String username);

    List<User> getAllUsers();

    User createUser(User user);
}
