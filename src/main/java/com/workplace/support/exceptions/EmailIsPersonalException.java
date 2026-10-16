package com.workplace.support.exceptions;

public class EmailIsPersonalException extends RuntimeException{
    public EmailIsPersonalException(String msg){
        super(msg);
    }
}
