package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// do not replace the testcontainer data source
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.3");

    @Test
    void testSaveUserAndFindById() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(UserRole.ROLE_ADMIN);

        userRepository.save(user);

        Optional<User> fetchedUser = userRepository.findById(user.getId());
        assertThat(fetchedUser)
                .isPresent()
                .contains(user);
    }

    @Test
    void testCanDeleteUser() {
        int numOfUsers = userRepository.findAll().size();

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(UserRole.ROLE_ADMIN);

        userRepository.save(user);

        assertThat(userRepository.findAll()).hasSize(numOfUsers + 1);
        assertThat(userRepository.findById(user.getId())).isPresent().contains(user);

        userRepository.deleteById(user.getId());

        assertThat(userRepository.findAll()).hasSize(numOfUsers);
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testUserCanBeFetchedByUsername() {
        Optional<User> userThatDoesNotExist = userRepository.findByUsername("random-username");
        assertThat(userThatDoesNotExist).isEmpty();

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(UserRole.ROLE_ADMIN);

        userRepository.save(user);

        Optional<User> fetchedUser = userRepository.findByUsername(user.getUsername());
        assertThat(fetchedUser)
                .isPresent()
                .contains(user);
    }
}