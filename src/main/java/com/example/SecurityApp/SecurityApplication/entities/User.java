package com.example.SecurityApp.SecurityApplication.entities;

import com.example.SecurityApp.SecurityApplication.entities.enums.Role;
import com.example.SecurityApp.SecurityApplication.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
//User Entity to fetch user details
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @ElementCollection(fetch=FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        Set<SimpleGrantedAuthority> authorities= roles.stream().map(role-> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());
//        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));
//         return authorities;

        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roles.forEach(role->{
            Set<SimpleGrantedAuthority> permissions= PermissionMapping.getAuthoritiesForRole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        });
        return authorities;
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
