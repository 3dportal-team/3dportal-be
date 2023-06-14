package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tdportal.mainservice.models.entities.ModelFileAccess;

import java.util.Optional;

public interface ModelFileAccessRepository extends JpaRepository<ModelFileAccess, Long> {

    Optional<ModelFileAccess> findByModelFile_IdAndUser_Id(Long modelFileId, Long userId);
}
