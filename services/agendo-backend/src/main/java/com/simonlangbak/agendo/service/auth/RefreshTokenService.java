package com.simonlangbak.agendo.service.auth;

import com.simonlangbak.agendo.domain.auth.RefreshToken;
import com.simonlangbak.agendo.domain.user.User;
import com.simonlangbak.agendo.repository.RefreshTokenRepository;
import com.simonlangbak.agendo.service.AbstractService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService extends AbstractService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value(value = "${security.refresh-token.expiration-time-ms}")
    private long refreshTokenExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(@NonNull User user) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);

        RefreshToken refreshToken;
        if (existingRefreshToken.isPresent()) {
            refreshToken = existingRefreshToken.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpirationDate(Instant.now().plusMillis(refreshTokenExpiration));
        } else {
            refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expirationDate(Instant.now().plusMillis(refreshTokenExpiration))
                .build();
        }

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }
}
