package com.simonlangbak.agendo.service.user;

import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.domain.user.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserLoaderService implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserLoaderService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@admin.com");
        admin.setRole(UserRole.ROLE_ADMIN);
        userService.createUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@user.com");
        user.setRole(UserRole.ROLE_USER);
        userService.createUser(user);

        alreadySetup = true;
    }
}
