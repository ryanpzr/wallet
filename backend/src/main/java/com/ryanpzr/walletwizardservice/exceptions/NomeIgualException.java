package com.ryanpzr.walletwizardservice.exceptions;

public class NomeIgualException extends Throwable {
    public NomeIgualException() {
    }

    public NomeIgualException(String message) {
        super(message);
    }

    public NomeIgualException(String message, Throwable cause) {
        super(message, cause);
    }

    public NomeIgualException(Throwable cause) {
        super(cause);
    }

    public NomeIgualException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
