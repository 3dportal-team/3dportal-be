package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.mainservice.models.entities.Instrument;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long>, JpaSpecificationExecutor<Instrument> {

    @Query(
            value = "SELECT * FROM instrument i WHERE i.author_id = :authorId",
            nativeQuery = true
    )
    List<Instrument> findInstrumentsByAuthorId(
            @Param("authorId") Long authorId
    );
}
