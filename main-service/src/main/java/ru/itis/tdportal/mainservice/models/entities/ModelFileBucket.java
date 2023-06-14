package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelFileBucket {

    private String generatedName;
    private String ownerEmail;
    private String entityTag;
    private byte[] file;

}
