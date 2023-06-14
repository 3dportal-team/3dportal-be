package ru.itis.tdportal.core.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.dtos.TokenDto;

import java.util.UUID;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    // TODO: убрать этот метод из библиотеки?
    public TokenDto generateTokens(PortalUserDto userDto) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String accessToken = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userDto.getId().toString())
                    .withClaim("email", userDto.getEmail())
                    .withClaim("role", userDto.getUserRole().toString())
                    .withClaim("redisUserId", userDto.getRedisUserId().toString())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withIssuer(issuer)
                    .sign(algorithm);

            TokenDto tokenDto = new TokenDto();
            tokenDto.setAccessToken(accessToken);
            tokenDto.setRefreshToken(refreshToken);
            return tokenDto;

            //TODO: добавить в токен время жизни
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getPayload() != null;
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

    }
}
