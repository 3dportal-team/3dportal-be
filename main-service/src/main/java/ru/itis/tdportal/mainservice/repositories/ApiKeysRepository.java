package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.mainservice.models.entities.ApiKey;

import java.util.Optional;

@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKey, Long> {

    @Query(
            value = "SELECT * FROM api_key ak WHERE ak.user_id = :userId",
            nativeQuery = true
    )
    Optional<ApiKey> findByUserId(@Param("userId") Long userId);
}
