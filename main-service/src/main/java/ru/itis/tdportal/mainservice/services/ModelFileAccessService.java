package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.ModelFileAccess;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;
import ru.itis.tdportal.mainservice.repositories.ModelFileAccessRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelFileAccessService {

    private final PortalUserService portalUserService;
    private final ModelFileAccessRepository repository;

    @Transactional
    public void saveAccess(
            ModelFile modelFile,
            Long creatorId,
            ModelToUserRelation relation) {
        PortalUser portalUser = portalUserService.getByIdOrElseThrow(creatorId);

        ModelFileAccess access = new ModelFileAccess();
        access.setModelFile(modelFile);
        access.setUser(portalUser);
        access.setRelation(relation);

        repository.save(access);
    }

    public boolean isModelFileExist(Long modelId, Long userId) {
        return getModelFileAccessByModelIdAndUserId(modelId, userId).isPresent();
    }

    @Transactional(readOnly = true)
    public Optional<ModelFileAccess> getModelFileAccessByModelIdAndUserId(Long modelId, Long userId) {
        return repository.findByModelFile_IdAndUser_Id(modelId, userId);
    }
}
