package com.simeon.lab4.dto;

import java.io.Serial;
import java.io.Serializable;

public class ErrorMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 6021047630844882133L;

    private String message;

    public ErrorMessage() {}

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
