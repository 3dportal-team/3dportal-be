package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;

import java.util.Optional;

@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {

    Optional<PortalUser> findByEmail(String email);
}
