package ru.itis.tdportal.mainservice.contollers.openapi;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.forms.ModelFileUploadFormDto;
import ru.itis.tdportal.mainservice.services.ModelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/models")
public class ModelController {

    private final ModelService modelService;

    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Загрузка файла модели и её метаинформации")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelFileDto uploadModel(@ModelAttribute ModelFileUploadFormDto fileUploadFormDto) {
        return modelService.saveModel(fileUploadFormDto);
    }

    @GetMapping
    @Operation(description = "Получение списка моделей с пагинацией")
    public Page<ModelFileDto> getModels(
            Pageable pageable
    ) {
        return modelService.getModels(pageable);
    }

    @GetMapping(value = "/{userId}")
    @Operation(description = "Получение модели по идентификатору пользователя")
    public ResponseEntity<?> getModelsByUserId(
            @PathVariable Long userId
    ) {
        List<ModelFileDto> modelFiles = modelService.getModelsByUserId(userId);
        return ResponseEntity.ok(modelFiles);
    }

    @GetMapping(value = "/model--{generatedName}")
    @Operation(description = "Получение модели по её сгенерированному имени")
    public ResponseEntity<?> getModelByGeneratedName(
            @PathVariable String generatedName
    ) {
        ModelFileDto modelFileDto = modelService.getModelByGeneratedName(generatedName);
        return ResponseEntity.ok(modelFileDto);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Обновление модели по её идентификатору")
    @PutMapping("/{modelId}")
    //TODO: добавить проверку доступа на редактирование
    public ModelFileDto updateModel(@PathVariable Long modelId,
                                    @RequestBody ModelFileDto dto) {
        dto.setId(modelId);
        return modelService.updateModel(dto);
    }
}
