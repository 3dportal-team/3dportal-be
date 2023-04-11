package ru.itis.tdportal.mainservice.models.enums;

import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;

import java.util.Objects;

public enum ModelToUserRelation {

    AVAILABLE_FOR_DOWNLOADING,
    UPLOADED;

    public static ModelToUserRelation calcRelation(ModelFileDto dto, Long userId) {
        PortalUserDto owner = dto.getOwner();
        if (owner.getId().equals(userId)) {
            return UPLOADED;
        }

        MoneyDto price = dto.getPrice();
        if (dto.getIsPurchased() || Objects.isNull(price) || Objects.isNull(price.getValue())) {
            return AVAILABLE_FOR_DOWNLOADING;
        }

        return null;
    }
}
