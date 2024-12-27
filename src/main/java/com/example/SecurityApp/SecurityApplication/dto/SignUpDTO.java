package com.example.SecurityApp.SecurityApplication.dto;

import com.example.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.example.SecurityApp.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
