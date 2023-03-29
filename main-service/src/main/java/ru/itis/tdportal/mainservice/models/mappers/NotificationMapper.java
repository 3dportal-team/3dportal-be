package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.NotificationDto;
import ru.itis.tdportal.mainservice.models.entities.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {
        PortalUserMapper.class
})
public interface NotificationMapper {

    Notification toEntity(NotificationDto dto);

    NotificationDto toDto(Notification entity);
}
