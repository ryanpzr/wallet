package com.ryanpzr.walletwizardservice.exceptions;

public class ReceitaExpiradaException extends Throwable {
    public ReceitaExpiradaException() {
    }

    public ReceitaExpiradaException(String message) {
        super(message);
    }

    public ReceitaExpiradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceitaExpiradaException(Throwable cause) {
        super(cause);
    }

    public ReceitaExpiradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
