package com.example.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
//User Entity to fetch user details
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //Return Password which later used by DAOAuthenticationManager
    @Override
    public String getPassword() {
        return this.password;
    }

    //Returns Username or email
    @Override
    public String getUsername() {
        return this.email;
    }
}
