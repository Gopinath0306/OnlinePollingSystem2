package com.exception;


public class InvalidVoterException extends Exception {

    
    private static final long serialVersionUID = 1L;

    public InvalidVoterException(String message) {
        super(message);
    }

}
