package ru.itis.tdportal.mainservice.contollers.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.core.dtos.TokenDto;
import ru.itis.tdportal.mainservice.dtos.forms.LoginFormDto;
import ru.itis.tdportal.mainservice.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationService authenticationService;

    @PostMapping(value = "/api/auth/login")
    public TokenDto login(@RequestBody LoginFormDto loginForm) {
        return authenticationService.login(loginForm);
    }
}
