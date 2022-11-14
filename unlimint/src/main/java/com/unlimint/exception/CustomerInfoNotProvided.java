package com.unlimint.exception;

public class CustomerInfoNotProvided extends RuntimeException {

    public CustomerInfoNotProvided() {
        super("The customer information provided could not be found.");
    }
}
