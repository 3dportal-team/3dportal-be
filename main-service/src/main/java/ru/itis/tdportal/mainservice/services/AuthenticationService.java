package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.dtos.TokenDto;
import ru.itis.tdportal.core.jwt.UserDetailsImpl;
import ru.itis.tdportal.core.models.exceptions.AuthenticationException;
import ru.itis.tdportal.core.services.JWTService;
import ru.itis.tdportal.mainservice.dtos.forms.LoginFormDto;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.exceptions.IncorrectUserCredentials;
import ru.itis.tdportal.mainservice.models.mappers.PortalUserMapper;
import ru.itis.tdportal.mainservice.repositories.PortalUserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RedisUserService redisUserService;
    private final PortalUserRepository portalUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PortalUserMapper userMapper;
    private final JWTService jwtService;

    @Transactional
    public TokenDto login(LoginFormDto loginForm) {
        PortalUser portalUser = portalUserRepository.findByEmail(loginForm.getEmail()).orElseThrow(
                () -> new IncorrectUserCredentials("Your email/password is incorrect!")
        );

        if (!passwordEncoder.matches(loginForm.getPassword(), portalUser.getHashPassword())) {
            throw new IncorrectUserCredentials("Your email/password is incorrect!");
        }

        if (Objects.isNull(portalUser.getRedisUserId())) {
            // TOD0: инкапсулировать сохранение
            String redisUserId = redisUserService.saveRedisUser(portalUser.getId());
            portalUser.setRedisUserId(UUID.fromString(redisUserId));
            portalUser = portalUserRepository.save(portalUser);
        }
        return jwtService.generateTokens(userMapper.toDto(portalUser));
    }

    public PortalUserDto getCurrentUser() {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails();

        return details.getUser();
    }

    public boolean isCurrentUserInfoExist() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        boolean isAuthenticated = authentication.isAuthenticated();
        UserDetailsImpl details = (UserDetailsImpl) authentication.getDetails();
        if (isAuthenticated && Objects.isNull(details)) {
            throw new AuthenticationException("User info is not found");
        }
        return isAuthenticated;
    }
}
