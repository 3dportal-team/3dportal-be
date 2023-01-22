package ru.itis.tdportal.mainservice.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormDto {

    private String email;
    private String password;

}