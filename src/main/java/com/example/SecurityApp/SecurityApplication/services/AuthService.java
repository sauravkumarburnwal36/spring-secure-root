package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.dto.LogInDTO;
import com.example.SecurityApp.SecurityApplication.dto.LoginResponseDTO;
import com.example.SecurityApp.SecurityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;
    public LoginResponseDTO login(LogInDTO logInDTO) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(),logInDTO.getPassword())
        );
        User user=(User)authentication.getPrincipal();
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        sessionService.generateNewSessions(user,refreshToken);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public Long getUserIdByLogin(LogInDTO logInDTO) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(),logInDTO.getPassword())
        );
        User user=(User)authentication.getPrincipal();
        return user.getId();
    }

    public Long getUserIdByToken(String token) {
        return jwtService.getUserIdFromToken(token);
    }

    public LoginResponseDTO refresh(String refreshToken) {
        Long userId= jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user=userService.getUserById(userId);
        String accessToken= jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }
}
