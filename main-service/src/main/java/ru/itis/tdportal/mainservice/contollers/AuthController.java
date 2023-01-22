package ru.itis.tdportal.mainservice.contollers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.core.dtos.TokenDto;
import ru.itis.tdportal.mainservice.dtos.MessageDto;
import ru.itis.tdportal.mainservice.dtos.forms.LoginFormDto;
import ru.itis.tdportal.mainservice.models.enums.MessageTypes;
import ru.itis.tdportal.mainservice.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationService authenticationService;

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDto loginForm) {
        try {
            TokenDto tokenDto = authenticationService.login(loginForm);
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            MessageDto messageDto = MessageDto.builder()
                    .message(e.getMessage())
                    .type(MessageTypes.ERROR)
                    .build();
            return ResponseEntity.badRequest().body(messageDto);
        }
    }
}
