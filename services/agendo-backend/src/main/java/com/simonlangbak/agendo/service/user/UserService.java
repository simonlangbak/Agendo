package com.simonlangbak.agendo.service.user;

import com.simonlangbak.agendo.domain.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getByUsername(String username);

    List<User> getAllUsers();

    User createUser(User user);
}
