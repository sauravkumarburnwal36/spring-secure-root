package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.dto.LogInDTO;
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
    public String login(LogInDTO logInDTO) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(),logInDTO.getPassword())
        );
        User user=(User)authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
