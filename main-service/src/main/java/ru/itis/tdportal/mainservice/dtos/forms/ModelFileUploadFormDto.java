package ru.itis.tdportal.mainservice.dtos.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.tdportal.mainservice.dtos.ModelFileDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModelFileUploadFormDto extends ModelFileDto {

    private MultipartFile modelFile;
}
