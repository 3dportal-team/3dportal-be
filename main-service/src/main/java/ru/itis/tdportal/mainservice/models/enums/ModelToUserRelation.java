package ru.itis.tdportal.mainservice.models.enums;

import lombok.Getter;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.models.exceptions.UnknownModelToUserRelationException;

import java.util.Objects;

@Getter
public enum  ModelToUserRelation {

    AVAILABLE_FOR_DOWNLOADING,
    UPLOADED,
    NOT_PURCHASED;

    public static ModelToUserRelation calcRelation(ModelFileDto dto, Long userId) {
        PortalUserDto owner = dto.getOwner();
        if (owner.getId().equals(userId)) {
            return UPLOADED;
        }

        MoneyDto price = dto.getPrice();
        if (dto.getIsPurchased() || Objects.isNull(price) || Objects.isNull(price.getValue())) {
            return AVAILABLE_FOR_DOWNLOADING;
        }

        if (!dto.getIsPurchased() && Objects.nonNull(price.getValue())) {
            return NOT_PURCHASED;
        }

        throw new UnknownModelToUserRelationException(
                String.format("Unknown relation between model--%s and current user", dto.getGeneratedName())
        );
    }
}
