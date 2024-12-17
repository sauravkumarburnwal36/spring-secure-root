package com.example.SecurityApp.SecurityApplication.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditedAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Saurav Kumar Burnwal");
    }
}
