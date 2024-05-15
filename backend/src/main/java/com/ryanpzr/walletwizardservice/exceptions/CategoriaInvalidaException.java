package com.ryanpzr.walletwizardservice.exceptions;

//Exception para verificar se a Categoria foi passada corretamente
public class CategoriaInvalidaException extends Throwable {
    public CategoriaInvalidaException() {
    }

    public CategoriaInvalidaException(String message) {
        super(message);
    }

    public CategoriaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoriaInvalidaException(Throwable cause) {
        super(cause);
    }

    public CategoriaInvalidaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
