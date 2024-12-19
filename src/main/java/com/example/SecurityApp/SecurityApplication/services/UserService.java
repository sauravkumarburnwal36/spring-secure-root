package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.example.SecurityApp.SecurityApplication.dto.UserDTO;
import com.example.SecurityApp.SecurityApplication.entities.User;
import com.example.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.example.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//Added User Service to loads all user by username
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                ()->{
                    throw new ResourceNotFoundException("User with email: "+username+" not found");
                }
        );
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<User> user=userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists,"+signUpDTO.getEmail());
        }
        User toBeCreated=modelMapper.map(signUpDTO,User.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));
        User savedUser=userRepository.save(toBeCreated);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
