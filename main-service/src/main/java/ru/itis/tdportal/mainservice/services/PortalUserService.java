package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.models.enums.PortalUserRole;
import ru.itis.tdportal.mainservice.dtos.ApiKeyDto;
import ru.itis.tdportal.mainservice.dtos.forms.PortalUserFormDto;
import ru.itis.tdportal.mainservice.models.entities.ApiKey;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.mappers.PortalUserMapper;
import ru.itis.tdportal.mainservice.repositories.ApiKeysRepository;
import ru.itis.tdportal.mainservice.repositories.PortalUserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortalUserService {

    private final PortalUserMapper portalUserMapper;
    private final PortalUserRepository portalUserRepository;
    private final ApiKeysRepository apiKeysRepository;
    private final ApiKeyService apiKeyService;

    public PortalUserDto updateUser(PortalUserFormDto portalUserForm) {
        PortalUser foundUser = getByIdOrElseThrow(portalUserForm.getId());
        if (Objects.nonNull(portalUserForm.getRole())) {
            foundUser.setUserRole(portalUserForm.getRole());
        }
        PortalUser changedUser = portalUserRepository.saveAndFlush(foundUser);
        return portalUserMapper.toDto(changedUser);
    }

    @Transactional(readOnly = true)
    public ApiKeyDto getApiKey(Long userId) {
        Optional<PortalUser> foundUser = portalUserRepository.findById(userId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            if (PortalUserRole.DEVELOPER.equals(portalUser.getUserRole())) {
                Optional<ApiKey> foundKey = apiKeysRepository.findByUserId(userId);
                if (foundKey.isPresent()) {
                    return ApiKeyDto.builder()
                            .apiKey(foundKey.get().value)
                            .build();
                }
            } else {
                String generatedKey = apiKeyService.generateApiKey();

                ApiKey savedApiKey = apiKeysRepository.saveAndFlush(ApiKey.builder()
                        .value(generatedKey)
                        .userId(portalUser)
                        .build()
                );

                return ApiKeyDto.builder()
                        .apiKey(savedApiKey.value)
                        .build();
            }
        }

        throw new UsernameNotFoundException(String.format("User with id: '%s' does not exist!", userId));
    }

    @Transactional(readOnly = true)
    public PortalUser getByIdOrElseThrow(Long userId) {
        return portalUserRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with id: '%s' does not exist!", userId)));
    }
}
