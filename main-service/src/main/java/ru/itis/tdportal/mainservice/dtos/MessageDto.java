package ru.itis.tdportal.mainservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.mainservice.models.enums.MessageTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    MessageTypes type;
    String message;
}
