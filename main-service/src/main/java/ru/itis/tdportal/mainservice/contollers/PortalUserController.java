package ru.itis.tdportal.mainservice.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.mainservice.dtos.MessageDto;
import ru.itis.tdportal.mainservice.dtos.forms.PortalUserFormDto;
import ru.itis.tdportal.mainservice.dtos.forms.RegisterFormDto;
import ru.itis.tdportal.mainservice.models.enums.MessageTypes;
import ru.itis.tdportal.mainservice.services.PortalUserService;
import ru.itis.tdportal.mainservice.services.ProfileService;
import ru.itis.tdportal.mainservice.services.RegisterService;

import javax.annotation.security.PermitAll;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class PortalUserController {

    private final PortalUserService portalUserService;
    private final ProfileService profileService;
    private final RegisterService registerService;

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeUser(
            @RequestBody PortalUserFormDto portalUserForm
    ) {
        return ResponseEntity.ok(
                portalUserService.updateUser(portalUserForm)
        );
    }

    @GetMapping("/{userId}/apiKey")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getApiKey(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                portalUserService.getApiKey(userId)
        );
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
                profileService.getUserProfileById(userId)
        );
    }

    @PermitAll
    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody RegisterFormDto registerForm) {
        try {
            boolean isOk = registerService.registerUser(registerForm);
            if (isOk) {
                MessageDto messageDto = MessageDto.builder()
                        .type(MessageTypes.SUCCESS)
                        .build();
                return ResponseEntity.ok(messageDto);
            } else {
                throw new Exception("Unknown error");
            }
        } catch (Exception e) {
            MessageDto messageDto = MessageDto.builder()
                    .message(e.getMessage())
                    .type(MessageTypes.ERROR)
                    .build();
            return ResponseEntity.badRequest().body(messageDto);
        }
    }

}
