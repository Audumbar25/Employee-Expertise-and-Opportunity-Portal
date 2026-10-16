package com.workplace.support.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Employee email must not be blank")
    @Email
    private String empEmail;

    @NotBlank(message = "Employee password must not be blank")
    @Size(min = 6 ,max = 10)
    private String empPassword;
}
