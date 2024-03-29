package ru.itis.tdportal.mainservice.contollers.openapi;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.mainservice.dtos.ApiKeyDto;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;
import ru.itis.tdportal.mainservice.dtos.ProfileDto;
import ru.itis.tdportal.mainservice.dtos.forms.PortalUserFormDto;
import ru.itis.tdportal.mainservice.dtos.forms.RegisterFormDto;
import ru.itis.tdportal.mainservice.models.filters.ModelFilter;
import ru.itis.tdportal.mainservice.services.ModelService;
import ru.itis.tdportal.mainservice.services.PortalUserService;
import ru.itis.tdportal.mainservice.services.ProfileService;
import ru.itis.tdportal.mainservice.services.RegisterService;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class PortalUserController {

    private final ModelService modelService;
    private final PortalUserService portalUserService;
    private final ProfileService profileService;
    private final RegisterService registerService;

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public PortalUserDto changeUser(@RequestBody PortalUserFormDto portalUserForm) {
        return portalUserService.updateUser(portalUserForm);
    }

    @GetMapping("/{userId}/apiKey")
    @PreAuthorize("isAuthenticated()")
    public ApiKeyDto getApiKey(@PathVariable Long userId) {
        return portalUserService.getApiKey(userId);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ProfileDto getProfileByUserId(@PathVariable Long userId) {
        return profileService.getUserProfileById(userId);
    }

    @PermitAll
    @PostMapping
    public Boolean registerAccount(@RequestBody RegisterFormDto registerForm) {
        return registerService.registerUser(registerForm);
    }

    @GetMapping(value = "/{userId}/models")
    @Operation(description = "Получение модели по идентификатору пользователя")
    public List<ModelFileDto> getModelsByUserId(ModelFilter filter, @PathVariable Long userId) {
        filter.setUserId(userId);
        return modelService.getModelsByFilter(filter);
    }
}
