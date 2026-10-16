package com.workplace.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpportunityResponseDto {

    private Long opId;
    private String title;
    private String description;
    private String skills;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime applyBefore;
}