package com.example.SecurityApp.SecurityApplication.utils;


import com.example.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.example.SecurityApp.SecurityApplication.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.example.SecurityApp.SecurityApplication.entities.enums.Role.*;

public class PermissionMapping {
    private static final Map<Role, Set<Permission>> map=Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(POST_CREATE,POST_UPDATE,USER_UPDATE),
            ADMIN,Set.of(POST_DELETE,USER_DELETE,POST_CREATE,USER_CREATE,POST_UPDATE,USER_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}
