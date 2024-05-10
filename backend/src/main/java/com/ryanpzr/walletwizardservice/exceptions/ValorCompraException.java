package com.ryanpzr.walletwizardservice.exceptions;

public class ValorCompraException extends Throwable {

    public ValorCompraException() {
    }

    public ValorCompraException(String message) {
        super(message);
    }

    public ValorCompraException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValorCompraException(Throwable cause) {
        super(cause);
    }

    public ValorCompraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
