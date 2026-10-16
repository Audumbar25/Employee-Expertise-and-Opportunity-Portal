package com.workplace.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpportunityRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotNull(message = "Apply before date is required")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime applyBefore;
}
