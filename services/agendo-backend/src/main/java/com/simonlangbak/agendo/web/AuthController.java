package com.simonlangbak.agendo.loginservice.web;

import com.simonlangbak.agendo.loginservice.domain.RefreshToken;
import com.simonlangbak.agendo.loginservice.domain.User;
import com.simonlangbak.agendo.loginservice.service.AuthenticationService;
import com.simonlangbak.agendo.loginservice.service.JwtAuthenticationService;
import com.simonlangbak.agendo.loginservice.service.RefreshTokenService;
import com.simonlangbak.agendo.loginservice.service.UserService;
import com.simonlangbak.agendo.loginservice.web.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            AuthenticationService authenticationService,
            JwtAuthenticationService jwtAuthenticationService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public JwtTokenDTO authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        User authenticatedUser = authenticationService
                .authenticate(loginRequestDTO.username(), loginRequestDTO.password()).orElse(null);
        if (authenticatedUser == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
        }

        return generateJwtToken(authenticatedUser);
    }

    @PostMapping("/refresh-token")
    @Transactional
    public JwtTokenDTO getNewAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshTokenDTO.refreshToken());
        if (optionalRefreshToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token not found - make a new sign-in");
        }

        RefreshToken refreshToken = optionalRefreshToken.get();
        if (!refreshTokenService.verifyExpiration(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired - make a new sign-in");
        }

        return generateJwtToken(refreshToken.getUser());
    }

    private JwtTokenDTO generateJwtToken(User user) {
        String accessToken = jwtAuthenticationService.generateToken(user);
        long accessTokenExpirationTime = jwtAuthenticationService.getExpirationTime();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new JwtTokenDTO(accessToken, refreshToken.getToken(), accessTokenExpirationTime);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User userToRegister = userRegistrationDTO.toUser(passwordEncoder);
        User registeredUser = userService.createUser(userToRegister);
        return UserDTO.fromDomain(registeredUser);
    }
}
