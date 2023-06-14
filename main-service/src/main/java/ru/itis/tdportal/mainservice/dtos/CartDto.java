package ru.itis.tdportal.mainservice.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class CartDto {

    private Set<ModelFileDto> modelFiles;
}
