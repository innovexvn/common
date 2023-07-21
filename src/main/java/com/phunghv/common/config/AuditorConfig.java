package com.phunghv.common.config;

import com.phunghv.common.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {
    private static final String SYSTEM_AUDITOR = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(SecurityUtils.getCurrentUsername());
        } catch (Exception ignored) {
        }
        return Optional.of(SYSTEM_AUDITOR);
    }
}
