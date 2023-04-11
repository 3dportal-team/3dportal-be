package ru.itis.tdportal.mainservice.models.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;

@Data
@Getter
@EqualsAndHashCode
@Schema(description = "Фильтр для выборки инструментов")
public class ModelFilter {

    @JsonIgnore
    private Long userId;

    private ModelToUserRelation userRelation;
}
