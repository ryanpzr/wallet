package com.ryanpzr.walletwizardservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

//Quando chamada, retorna uma mensagem ao usuario
@Getter
@Setter
@AllArgsConstructor
public class MensagensDeErro {

    private HttpStatus status;
    private String mensagem;

}
