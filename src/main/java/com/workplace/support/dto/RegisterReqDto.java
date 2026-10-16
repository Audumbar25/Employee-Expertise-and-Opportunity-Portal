package com.workplace.support.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterReqDto {

    @NotBlank(message = "Employee name is required")
    private String empName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String empEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    private String empPassword;
}
