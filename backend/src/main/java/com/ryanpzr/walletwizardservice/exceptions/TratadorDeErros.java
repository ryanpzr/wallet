package com.ryanpzr.walletwizardservice.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {


    @ExceptionHandler(CategoriaInvalidaException.class)
    public ResponseEntity<MensagensDeErro> tratarErroIAE(CategoriaInvalidaException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagensDeErro> tratarErroNF(EntityNotFoundException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    @ExceptionHandler(ValorCompraException.class)
    public ResponseEntity<MensagensDeErro> tratarErroVCE(ValorCompraException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    @ExceptionHandler(ReceitaExpiradaException.class)
    public ResponseEntity<MensagensDeErro> tratarErroSIE(ReceitaExpiradaException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagensDeErro> tratarErroIAEX(IllegalArgumentException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    @ExceptionHandler(ListIsNullException.class)
    public ResponseEntity<MensagensDeErro> tratarErroLINE(ListIsNullException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    @ExceptionHandler(NomeIgualException.class)
    public ResponseEntity<MensagensDeErro> tratarErroNIE(NomeIgualException ex) {
        MensagensDeErro mensagem = new MensagensDeErro(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }
}
