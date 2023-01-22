package ru.itis.tdportal.mainservice.models.mappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.services.BucketFileService;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@Named("ModelFileUtility")
@RequiredArgsConstructor
public class ModelFileUtility {

    private final BucketFileService bucketFileService;

    @Named("getPreSignedUrl")
    String getPreSignedUrl(ModelFile modelFile) {
        return bucketFileService.findPreSignedUrlFromModelFile(modelFile);
    }

    @Named("getMimeType")
    String getMimeType(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return Objects.requireNonNull(originalFileName).substring(
                originalFileName.lastIndexOf(".")
        );
    }

    @Named("getGeneratedName")
    String getGeneratedName(ModelFileDto source) {
        String generatedName = source.getGeneratedName();
        return Objects.isNull(source.getGeneratedName()) ? UUID.randomUUID().toString() : generatedName;
    }
}
