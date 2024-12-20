package com.example.SecurityApp.SecurityApplication.controllers;

import com.example.SecurityApp.SecurityApplication.dto.LogInDTO;
import com.example.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.example.SecurityApp.SecurityApplication.dto.UserDTO;
import com.example.SecurityApp.SecurityApplication.services.AuthService;
import com.example.SecurityApp.SecurityApplication.services.SessionService;
import com.example.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllers {
    private final UserService userService;
    private final AuthService authService;
    private final SessionService sessionService;
    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO=userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> login(@RequestBody LogInDTO logInDTO, HttpServletRequest request,HttpServletResponse response){
        String token=authService.login(logInDTO);
        Long userId=authService.getUserIdByLogin(logInDTO);
        sessionService.createSession(userId,token);
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

   @GetMapping("/validate")
    public ResponseEntity<String> isvalidToken(@CookieValue("token") String token)
   {
       if(sessionService.isValidToken(token))
       {
           return ResponseEntity.ok("Valid Token");
       }
       return ResponseEntity.status(401).body("Invalid Token");
   }

   @GetMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue("token")String token,HttpServletResponse response){
        Long userId=authService.getUserIdByToken(token);
        sessionService.removeSession(userId);
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok("User Logged Out");
   }
}
