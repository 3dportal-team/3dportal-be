package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.tdportal.core.constants.ExceptionStrings;
import ru.itis.tdportal.core.dtos.PortalUserDto;
import ru.itis.tdportal.core.jwt.UserDetailsImpl;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.mappers.PortalUserMapper;
import ru.itis.tdportal.mainservice.repositories.PortalUserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Component("jwtUserDetailsImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PortalUserRepository portalUserRepository;
    private final PortalUserMapper portalUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PortalUser> foundUser = portalUserRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            PortalUserDto user = portalUserMapper.toDto(portalUser);
            return new UserDetailsImpl(user);
        }

        throw new UsernameNotFoundException(ExceptionStrings.USER_WITH_EMAIL_DOES_NOT_EXIST(email));
    }
}
