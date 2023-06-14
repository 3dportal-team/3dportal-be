package ru.itis.tdportal.mainservice.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.itis.tdportal.mainservice.models.entities.Instrument;
import ru.itis.tdportal.mainservice.models.filters.InstrumentFilter;

import java.util.Objects;

@Component
public class InstrumentSpecification {

    public Specification<Instrument> byFilter(InstrumentFilter filter) {
        return Objects.isNull(filter) ? null : Specification
                .where(byHosted(filter.isHosted()));
    }

    private Specification<Instrument> byHosted(Boolean hosted) {
        return Objects.isNull(hosted) ? null : (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("hostUrl"));
    }
}
