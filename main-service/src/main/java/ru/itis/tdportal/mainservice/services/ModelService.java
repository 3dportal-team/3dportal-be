package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.forms.ModelFileUploadFormDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.ModelFileBucket;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;
import ru.itis.tdportal.mainservice.models.exceptions.ModelFileAccessException;
import ru.itis.tdportal.mainservice.models.exceptions.ModelFileNotFoundException;
import ru.itis.tdportal.mainservice.models.filters.ModelFilter;
import ru.itis.tdportal.mainservice.models.mappers.ModelFileMapper;
import ru.itis.tdportal.mainservice.repositories.ModelFileRepository;
import ru.itis.tdportal.mainservice.repositories.specifications.ModelFileSpecification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final AuthenticationService authService;
    private final PortalUserService portalUserService;
    private final ModelFileAccessService modelFileAccessService;

    private final ModelFileRepository modelFileRepository;
    private final ModelFileBucketRepository modelFileBucketRepository;
    private final ModelFileMapper modelFileMapper;
    private final ModelFileSpecification specification;

    @Transactional
    public ModelFileDto saveModel(ModelFileUploadFormDto uploadFormDto) {
        PortalUserDto currentUser = authService.getCurrentUser();
        uploadFormDto.setOwner(currentUser);

        ModelFileBucket bucket = modelFileMapper.toBucketEntity(uploadFormDto);
        ModelFileBucket newBucket = modelFileBucketRepository.saveFile(bucket);

        ModelFile modelFile = modelFileMapper.toModelFileEntity(uploadFormDto);
        modelFile.setEntityTag(newBucket.getEntityTag());

        ModelFile savedModelFile = modelFileRepository.save(modelFile);
        modelFileAccessService.saveAccess(savedModelFile, currentUser.getId(), ModelToUserRelation.UPLOADED);
        return modelFileMapper.toDto(savedModelFile);
    }

    @Transactional(readOnly = true)
    public ModelFileDto getModelByGeneratedName(String givenName) {
        ModelFile modelFile = modelFileRepository.findByGeneratedName(givenName)
                .orElseThrow(() -> new ModelFileNotFoundException(
                        String.format("Model with given name = %s isn't found", givenName))
                );

        ModelFileDto dto = modelFileMapper.toDto(modelFile);

        Long currentUserId = null;
        if (authService.isCurrentUserInfoExist()) {
            PortalUserDto currentUser = authService.getCurrentUser();
            boolean isPurchased = modelFileAccessService.isModelFileExist(
                    dto.getId(),
                    currentUser.getId()
            );
            currentUserId = currentUser.getId();
            dto.setIsPurchased(isPurchased);
        } else {
            dto.setIsPurchased(false);
        }

        dto.setUserRelation(
                ModelToUserRelation.calcRelation(dto, currentUserId)
        );

        return dto;
    }

    @Transactional(readOnly = true)
    public Page<ModelFileDto> getModels(Pageable pageable) {
        Page<ModelFile> modelFiles = modelFileRepository.findAll(pageable);
        return modelFiles.map(modelFileMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ModelFileDto> getModelsByFilter(ModelFilter filter) {
        portalUserService.getByIdOrElseThrow(filter.getUserId());
        List<ModelFile> modelFiles = modelFileRepository.findAll(specification.byFilter(filter));
        return modelFileMapper.toListDto(modelFiles).stream()
                .peek(model -> model.setUserRelation(filter.getUserRelation()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ModelFile getModelFileOrThrow(Long modelId) {
        return modelFileRepository.findById(modelId)
                .orElseThrow(() -> new ModelFileNotFoundException(String.format("Model file not found by id = %s", modelId)));
    }

    @Transactional(readOnly = true)
    public Set<ModelFileDto> getModelFilesByIds(Set<Long> ids) {
        return modelFileMapper.toSetDto(modelFileRepository.findAllById(ids));
    }

    @Transactional
    public ModelFileDto updateModel(ModelFileDto dto) {
        ModelFile modelFile = getModelFileOrThrow(dto.getId());
        PortalUserDto currentUser = authService.getCurrentUser();

        Long ownerId = modelFile.getOwner().getId();
        Long currentUserId = currentUser.getId();

        if (ownerId.equals(currentUserId)) {
            modelFileMapper.merge(modelFile, dto);
            return modelFileMapper.toDto(modelFile);
        }

        throw new ModelFileAccessException(String.format(
                "Current user with id = %s hasn't access to update model with id = %s", currentUser, dto.getId())
        );
    }
}