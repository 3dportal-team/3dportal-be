package ru.itis.tdportal.mainservice.models.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.InstrumentDto;
import ru.itis.tdportal.mainservice.dtos.forms.InstrumentFormDto;
import ru.itis.tdportal.mainservice.models.entities.Instrument;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {
        PortalUserMapper.class
})
public interface InstrumentMapper {

    @Mapping(target = "author.id", source = "dto.authorId")
    Instrument toEntity(InstrumentFormDto dto);

    List<InstrumentDto> toListDto(List<Instrument> entity);

    @Mapping(target = "entity.id", ignore = true)
    Instrument merge(@MappingTarget Instrument entity, InstrumentDto dto);

    InstrumentDto toDto(Instrument entity);
}
