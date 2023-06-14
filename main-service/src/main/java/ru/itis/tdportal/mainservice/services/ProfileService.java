package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.mainservice.dtos.ProfileDto;
import ru.itis.tdportal.mainservice.models.entities.Profile;
import ru.itis.tdportal.mainservice.models.mappers.ProfileMapper;
import ru.itis.tdportal.mainservice.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    public ProfileDto getUserProfileById(Long id) {
        Profile profile = profileRepository.getById(id);
        return profileMapper.toDto(profile);
    }
}
