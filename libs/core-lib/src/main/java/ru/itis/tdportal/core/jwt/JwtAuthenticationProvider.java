package ru.itis.tdportal.core.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.models.enums.PortalUserRole;
import ru.itis.tdportal.core.models.exceptions.AuthenticationException;

import java.util.UUID;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = authentication.getName();

        DecodedJWT decodedJWT;

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            authentication.setAuthenticated(false);

            throw new AuthenticationException("Bad token");
        }

        Long id = Long.valueOf(decodedJWT.getSubject());
        String email = decodedJWT.getClaim("email").asString();
        PortalUserRole role = PortalUserRole.valueOf(decodedJWT.getClaim("role").asString());
        String redisUserID = decodedJWT.getClaim("redisUserId").asString();

        PortalUserDto user = new PortalUserDto();
        user.setId(id);
        user.setEmail(email);
        user.setUserRole(role);
        user.setRedisUserId(UUID.fromString(redisUserID));
        UserDetails userDetails = new UserDetailsImpl(user);
        authentication.setAuthenticated(true);
        ((JwtAuthentication) authentication).setUserDetails(userDetails);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }


}