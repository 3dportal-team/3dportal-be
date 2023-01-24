package ru.itis.tdportal.mainservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.core.dtos.PortalUserDto;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelFileDto {

    private Long id;
    private String givenName;
    private String modelUrl;
    private String description;
    private String previewImageUrl;
    private String generatedName;
    private String mimeType;
    private Instant createdAt;
    private Instant lastModifiedAt;
    private PortalUserDto owner;
    private MoneyDto price;

}
