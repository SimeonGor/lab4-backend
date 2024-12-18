package com.simeon.lab4.ejb.expetions;

public class UserExistException extends Exception {
    public UserExistException(String message) {
        super(message);
    }
}
