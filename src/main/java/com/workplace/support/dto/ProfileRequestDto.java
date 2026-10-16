package com.workplace.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileRequestDto {
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Contact number must contain exactly 10 digits")
    private String contactNumber;

    @NotBlank(message = "Designation is required")
    @Size(max = 50, message = "Designation cannot exceed 50 characters")
    private String designation;

    @NotBlank(message = "Skills are required")
    @Size(max = 500, message = "Skills cannot exceed 500 characters")
    private String skills;

    @NotBlank(message = "Expertise modules are required")
    @Size(max = 500, message = "Expertise modules cannot exceed 500 characters")
    private String expertiseModules;
}
