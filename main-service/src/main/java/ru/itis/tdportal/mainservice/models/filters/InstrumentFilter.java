package ru.itis.tdportal.mainservice.models.filters;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@Getter
@EqualsAndHashCode
@Schema(description = "Фильтр для выборки инструментов")
public class InstrumentFilter {

    private Long authorId;
    private boolean hosted;
}
