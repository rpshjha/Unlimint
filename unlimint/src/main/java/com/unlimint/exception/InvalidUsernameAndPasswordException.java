package com.unlimint.exception;

public class InvalidUsernameAndPasswordException extends RuntimeException {

    public InvalidUsernameAndPasswordException() {
        super("The username and password could not be verified.");
    }
}
