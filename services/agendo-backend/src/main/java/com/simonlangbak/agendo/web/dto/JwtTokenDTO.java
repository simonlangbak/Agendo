package com.simonlangbak.agendo.loginservice.web.dto;

/**
 *
 * @param accessToken token should be provided in the header of every request to secured endpoint
 * @param refreshToken used to refresh token when access token has expired
 * @param expiresIn specifies the lifespan of a accessToken in millis
 */
public record JwtTokenDTO(String accessToken, String refreshToken, long expiresIn) {
}
