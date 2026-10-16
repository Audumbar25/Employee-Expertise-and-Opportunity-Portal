package com.workplace.support.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
}
