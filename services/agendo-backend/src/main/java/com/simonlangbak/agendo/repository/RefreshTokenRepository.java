package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.auth.RefreshToken;
import com.simonlangbak.agendo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
}
