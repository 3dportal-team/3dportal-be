package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PortalUserMapper {

    PortalUserDto toDto(PortalUser user);

}
