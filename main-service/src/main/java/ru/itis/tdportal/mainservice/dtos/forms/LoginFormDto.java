package ru.itis.tdportal.mainservice.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDto {

    private String email;
    private String password;

}