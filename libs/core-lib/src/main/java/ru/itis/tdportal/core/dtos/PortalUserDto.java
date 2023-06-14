package ru.itis.tdportal.core.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.core.models.enums.PortalUserRole;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PortalUserDto {

    private Long id;
    private String email;
    private PortalUserRole userRole;

    @JsonIgnore
    private UUID redisUserId;

}
