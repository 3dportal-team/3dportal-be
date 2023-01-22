package ru.itis.tdportal.mainservice.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentFormDto {

    private Long authorId;
    private String name;
    private String description;

}
