package ru.itis.tdportal.mainservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.core.dtos.PortalUserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentDto {

    private Long id;
    private String name;
    private String description;
    private String hostUrl;
    private PortalUserDto author;
}
