package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.forms.ModelFileUploadFormDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.payment.models.models.mappers.MoneyMapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PortalUserMapper.class, ModelFileUtility.class, MoneyMapper.class})
public interface ModelFileMapper {

    @Mapping(target = "modelUrl", source = "source", qualifiedByName = {"ModelFileUtility", "getPreSignedUrl"})
    ModelFileDto toDto(ModelFile source);

    List<ModelFileDto> toListDto(List<ModelFile> source);

    Set<ModelFileDto> toSetDto(List<ModelFile> source);

    @Mapping(target = "originalFileName", expression = "java(source.getModelFile().getOriginalFilename())")
    @Mapping(target = "mimeType", source = "source.modelFile", qualifiedByName = {"ModelFileUtility", "getMimeType"})
    @Mapping(target = "generatedName", source = "source", qualifiedByName = {"ModelFileUtility", "getGeneratedName"})
    ModelFile toEntity(ModelFileUploadFormDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originalFileName", ignore = true)
    @Mapping(target = "generatedName", ignore = true)
    @Mapping(target = "entityTag", ignore = true)
    @Mapping(target = "mimeType", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    void merge(@MappingTarget ModelFile oldModelFile, ModelFileDto dto);
}
