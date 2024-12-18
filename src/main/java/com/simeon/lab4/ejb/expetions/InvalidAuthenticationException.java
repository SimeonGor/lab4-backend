package com.simeon.lab4.ejb.expetions;

public class InvalidAuthenticationException extends Exception {
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
