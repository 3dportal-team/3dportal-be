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
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.exceptions.ModelFileNotFound;
import ru.itis.tdportal.mainservice.models.mappers.ModelFileMapper;
import ru.itis.tdportal.mainservice.repositories.ModelFileRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final AuthenticationService authService;
    private final ModelFileRepository modelFileRepository;
    private final PortalUserService portalUserService;
    private final BucketFileService bucketFileService;
    private final ModelFileMapper modelFileMapper;

    @Transactional
    public ModelFileDto saveModel(ModelFileUploadFormDto uploadFormDto) {
        PortalUserDto currentUser = authService.getCurrentUser();
        uploadFormDto.setOwner(currentUser);

        String entityTag = bucketFileService.saveFile(uploadFormDto);

        ModelFile modelFile = modelFileMapper.toEntity(uploadFormDto);
        modelFile.setEntityTag(entityTag);

        return modelFileMapper.toDto(modelFileRepository.save(modelFile));
    }

    public ModelFileDto getModelByGeneratedName(String givenName) {
        ModelFile modelFile = modelFileRepository.findByGeneratedName(givenName)
                .orElseThrow(() -> new ModelFileNotFound(
                        String.format("Model with given name = %s isn't found", givenName))
                );
        return modelFileMapper.toDto(modelFile);
    }

    public Page<ModelFileDto> getModels(Pageable pageable) {
        Page<ModelFile> modelFiles = modelFileRepository.findAll(pageable);
        return modelFiles.map(modelFileMapper::toDto);
    }

    public List<ModelFileDto> getModelsByUserId(Long userId) {
        PortalUser portalUser = portalUserService.getByIdOrElseThrow(userId);
        List<ModelFile> userModelFiles = portalUser.getModels();

        return modelFileMapper.toListDto(userModelFiles);
    }

    @Transactional(readOnly = true)
    public ModelFile getModelFileOrThrow(Long modelId) {
        return modelFileRepository.findById(modelId)
                .orElseThrow(() -> new ModelFileNotFound(String.format("Model file not found by id = %s", modelId)));
    }

    @Transactional(readOnly = true)
    public Set<ModelFileDto> getModelFilesById(Set<Long> ids) {
        return modelFileMapper.toSetDto(modelFileRepository.findAllById(ids));
    }

    @Transactional
    public ModelFileDto updateModel(ModelFileDto dto) {
        ModelFile modelFile = getModelFileOrThrow(dto.getId());
        modelFileMapper.merge(modelFile, dto);
        return modelFileMapper.toDto(modelFile);
    }
}