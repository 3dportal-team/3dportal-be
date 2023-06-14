package ru.itis.tdportal.core.models.auditors;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.jwt.UserDetailsImpl;

import java.util.Objects;
import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.isNull(securityContext)) {
            return Optional.empty();
        }

        Authentication authentication = securityContext.getAuthentication();
        if (Objects.isNull(authentication)) {
            return Optional.empty();
        }

        UserDetailsImpl details = (UserDetailsImpl) authentication.getDetails();
        if (Objects.isNull(details)) {
            return Optional.empty();
        }

        PortalUserDto user = details.getUser();
        return Optional.ofNullable(Objects.isNull(user)? null : user.getId());
    }
}