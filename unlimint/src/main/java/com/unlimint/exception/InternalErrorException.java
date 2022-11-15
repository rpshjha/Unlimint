package com.unlimint.exception;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String username) {
        super("Internal Error occurred..");
    }
}
