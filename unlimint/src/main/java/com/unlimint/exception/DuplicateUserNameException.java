package com.unlimint.exception;

public class DuplicateUserNameException extends RuntimeException {

    public DuplicateUserNameException(String username) {
        super("The username =>" + username + " already exists.");
    }
}
