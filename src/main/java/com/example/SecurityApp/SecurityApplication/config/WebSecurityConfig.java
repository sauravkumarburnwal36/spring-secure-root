package com.example.SecurityApp.SecurityApplication.config;

import com.example.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import com.example.SecurityApp.SecurityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/posts","/auth/**","/error","/home.html").permitAll()
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
