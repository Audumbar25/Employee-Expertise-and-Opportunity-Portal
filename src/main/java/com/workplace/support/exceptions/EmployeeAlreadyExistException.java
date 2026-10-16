package com.workplace.support.exceptions;

public class EmployeeAlreadyExistException extends RuntimeException{

    public EmployeeAlreadyExistException(String msg){
        super(msg);
    }
}
