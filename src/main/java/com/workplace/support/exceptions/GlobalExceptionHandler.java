package com.workplace.support.exceptions;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeAlreadyExists(EmployeeAlreadyExistException exception){
        ErrorResponse resp = new ErrorResponse();
        resp.setStatus(HttpStatus.CONFLICT.value());
        resp.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    @ExceptionHandler(EmailIsPersonalException.class)
    public ResponseEntity<ErrorResponse> personalMailNotAllowed(EmailIsPersonalException exception){
        ErrorResponse resp = new ErrorResponse();
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCrdentials(InvalidCredentialsException exception){
        ErrorResponse resp = new ErrorResponse();
        resp.setMessage(exception.getMessage());
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> employeeNotFound(EmployeeNotFoundException exception){
        ErrorResponse resp = new ErrorResponse();
        resp.setStatus(HttpStatus.NOT_FOUND.value());
        resp.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentExceptions(MethodArgumentNotValidException exception){
        String msg = "Validation Failed";
        if(exception.getBindingResult().getFieldError()!=null && exception.getBindingResult().getFieldError().getDefaultMessage()!=null){
            msg = exception.getBindingResult().getFieldError().getDefaultMessage();
        }
        ErrorResponse resp = new ErrorResponse();
        resp.setStatus((HttpStatus.BAD_REQUEST.value()));
        resp.setMessage(msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(resp);
    }

    @ExceptionHandler(OpportunityDoesNotExist.class)
    public ResponseEntity<ErrorResponse> handleOpportunityDoesNotExist(OpportunityDoesNotExist exception){
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }
}
