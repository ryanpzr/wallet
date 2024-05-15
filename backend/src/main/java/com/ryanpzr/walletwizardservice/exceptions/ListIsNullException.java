package com.ryanpzr.walletwizardservice.exceptions;

//Verifica se a lista esta vindo nula
public class ListIsNullException extends Throwable {

    public ListIsNullException() {
    }

    public ListIsNullException(String message) {
        super(message);
    }

    public ListIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListIsNullException(Throwable cause) {
        super(cause);
    }

    public ListIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
