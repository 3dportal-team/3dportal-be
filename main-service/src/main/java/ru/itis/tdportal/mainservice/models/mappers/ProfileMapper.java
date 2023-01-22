package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.ProfileDto;
import ru.itis.tdportal.mainservice.models.entities.Profile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @Mapping(target = "email", source = "profile.user.email")
    ProfileDto toDto(Profile profile);
}
