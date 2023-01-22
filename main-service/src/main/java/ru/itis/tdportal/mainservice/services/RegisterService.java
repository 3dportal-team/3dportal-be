package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.core.constants.ExceptionStrings;
import ru.itis.tdportal.core.models.enums.PortalUserRole;
import ru.itis.tdportal.mainservice.dtos.forms.RegisterFormDto;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.entities.Profile;
import ru.itis.tdportal.mainservice.repositories.PortalUserRepository;
import ru.itis.tdportal.mainservice.repositories.ProfileRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PortalUserRepository portalUserRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registerUser(RegisterFormDto registerForm) {
        portalUserRepository.findByEmail(registerForm.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException(ExceptionStrings.USER_WITH_EMAIL_ALREADY_EXIST(user.getEmail()));
                });

        PortalUser newPortalUser = PortalUser.builder()
                .hashPassword(passwordEncoder.encode(registerForm.getPassword()))
                .email(registerForm.getEmail())
                .userRole(PortalUserRole.BASIC)
                .build();
        PortalUser successPortalUser = portalUserRepository.save(newPortalUser);

        Profile profile = Profile.builder()
                .id(successPortalUser.getId())
                .build();
        Profile savedProfile = profileRepository.save(profile);
        successPortalUser.setProfile(savedProfile);
        portalUserRepository.save(successPortalUser);

        return Objects.nonNull(successPortalUser.getId());

    }
}
