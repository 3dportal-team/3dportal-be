package ru.itis.tdportal.mainservice.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.core.models.enums.PortalUserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalUserFormDto {

    private Long id;
    private PortalUserRole role;
}
