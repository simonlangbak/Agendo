package com.simonlangbak.agendo.config.filter;

import com.simonlangbak.agendo.config.SecurityConfig;
import com.simonlangbak.agendo.service.auth.JwtAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static RequestMatcher requestMatcher;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserDetailsService userDetailsService;

    static {
        final String[] publicEndPoints = {SecurityConfig.AUTH_API};
        ignorePatterns(publicEndPoints);
    }

    public JwtAuthenticationFilter(JwtAuthenticationService jwtAuthenticationService, UserDetailsService userDetailsService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.userDetailsService = userDetailsService;
    }

    public static void ignorePatterns(String... antPatterns) {
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : antPatterns) {
            matchers.add(new AntPathRequestMatcher(pattern, null));
        }
        requestMatcher = new OrRequestMatcher(matchers);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwtToken = authHeader.substring(7);
            final String username = jwtAuthenticationService.extractUsername(jwtToken);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtAuthenticationService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token expires - use refresh token or make a new sign-in");
        }
    }
}
