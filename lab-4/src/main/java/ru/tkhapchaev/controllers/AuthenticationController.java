package ru.tkhapchaev.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tkhapchaev.services.token.TokenService;
import ru.tkhapchaev.services.user.UsrDetails;
import ru.tkhapchaev.services.user.UsrDetailsService;

@RestController
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final UsrDetailsService userDetailsService;

    public AuthenticationController(TokenService tokenService, AuthenticationManager authManager,
                                    UsrDetailsService userDetailsService) {
        super();

        this.tokenService = tokenService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    public record LoginRequest(String username, String password) {
    }

    public record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {
    }

    public record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username, request.password);

        authManager.authenticate(authenticationToken);
        UsrDetails user = (UsrDetails) userDetailsService.loadUserByUsername(request.username);

        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new LoginResponse("User " + request.username + " successfully logined", access_token, refresh_token);
    }

    @GetMapping("/token/refresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String refreshToken = headerAuth.substring(7, headerAuth.length());

        String email = tokenService.parseToken(refreshToken);
        UsrDetails user = (UsrDetails) userDetailsService.loadUserByUsername(email);

        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new RefreshTokenResponse(access_token, refresh_token);
    }
}