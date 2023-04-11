package ru.itis.tdportal.mainservice.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.ModelFileAccess;
import ru.itis.tdportal.mainservice.models.entities.PortalUser;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;
import ru.itis.tdportal.mainservice.models.filters.ModelFilter;

import javax.persistence.criteria.Join;
import java.util.Objects;

@Component
public class ModelFileSpecification {

    public Specification<ModelFile> byFilter(ModelFilter filter) {
        return Objects.isNull(filter) ? null : Specification
                .where(byUserID(filter.getUserId()))
                .and(byRelation(filter.getUserRelation()));
    }

    private Specification<ModelFile> byRelation(ModelToUserRelation userRelation) {
        return Objects.isNull(userRelation) ? null : (root, query, criteriaBuilder) -> {
            Join<ModelFile, ModelFileAccess> modelFileAccess = root.join(PortalUser.Fields.accesses);
            return criteriaBuilder.equal(modelFileAccess.get(ModelFileAccess.Fields.relation), userRelation);
        };
    }

    private Specification<ModelFile> byUserID(Long userId) {
        return Objects.isNull(userId) ? null : (root, query, criteriaBuilder) -> {
            Join<ModelFile, ModelFileAccess> modelFileAccess = root.join(PortalUser.Fields.accesses);
            return criteriaBuilder.equal(modelFileAccess.get(ModelFileAccess.Fields.user).get(PortalUser.Fields.id), userId);
        };
    }
}
