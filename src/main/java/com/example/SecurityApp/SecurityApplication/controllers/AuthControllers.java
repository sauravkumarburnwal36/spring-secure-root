package com.example.SecurityApp.SecurityApplication.controllers;

import com.example.SecurityApp.SecurityApplication.dto.LogInDTO;
import com.example.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.example.SecurityApp.SecurityApplication.dto.UserDTO;
import com.example.SecurityApp.SecurityApplication.services.AuthService;
import com.example.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllers {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO=userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> login(@RequestBody LogInDTO logInDTO, HttpServletRequest request,HttpServletResponse response){
        String token=authService.login(logInDTO);
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }
}
