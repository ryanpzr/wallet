package com.ryanpzr.walletwizardservice.model.expense;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ryanpzr.walletwizardservice.exceptions.CategoriaInvalidaException;

public enum Category {

    LAZER,
    MERCADO,
    INVESTIMENTO,
    ALIMENTACAO,
    TRANSPORTE,
    MORADIA;

    @JsonCreator
    public static Category fromString(String value) throws CategoriaInvalidaException {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new CategoriaInvalidaException("Especialidade inválida: " + value);
    }

}
