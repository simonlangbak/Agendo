package com.simonlangbak.agendo.service.auth;

import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.repository.UserRepository;
import com.simonlangbak.agendo.service.AbstractService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService extends AbstractService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return userRepository.findByUsername(username);
    }
}
