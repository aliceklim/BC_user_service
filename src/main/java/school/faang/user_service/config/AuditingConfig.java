package school.faang.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // wire this into SecurityContextHolder later
        // return () -> Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        return () -> Optional.of("system-user");
    }
}
