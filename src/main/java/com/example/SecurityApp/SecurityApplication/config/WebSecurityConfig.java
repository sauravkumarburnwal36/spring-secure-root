package com.example.SecurityApp.SecurityApplication.config;

import com.example.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import com.example.SecurityApp.SecurityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.example.SecurityApp.SecurityApplication.entities.enums.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String[] publicRoutes={"/auth/**","/error","/home.html"};
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(publicRoutes).permitAll()
                                .requestMatchers(HttpMethod.GET,"/posts").permitAll()
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAuthority(POST_DELETE.name())
//                                .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(
                        sessionManagementConfig->
                                sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config-> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler));
               // .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    UserDetailsService inMemoryUserDetailsManager(){
//        UserDetails normalUser= User
//                .withUsername("Saurav").password(passwordEncoder().encode("Saurav@123"))
//                .roles("USER").build();
//        UserDetails adminUser= User.withUsername("Admin").password(passwordEncoder().encode("Admin@123"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

}
