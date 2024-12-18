package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.example.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
@RequiredArgsConstructor
//Added User Service to loads all user by username
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                ()->{
                    throw new ResourceNotFoundException("User with email: "+username+" not found");
                }
        );
    }
}
