package com.workplace.support.exceptions;

public class OpportunityDoesNotExist extends RuntimeException{
    public OpportunityDoesNotExist(String message) {
        super(message);
    }
}
