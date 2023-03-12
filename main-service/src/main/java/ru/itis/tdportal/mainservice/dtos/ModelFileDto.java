package ru.itis.tdportal.mainservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private Boolean isPurchased;
    private ModelToUserRelation userRelation;
}
