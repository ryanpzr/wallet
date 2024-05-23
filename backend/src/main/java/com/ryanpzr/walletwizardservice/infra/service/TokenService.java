package com.ryanpzr.walletwizardservice.infra.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ryanpzr.walletwizardservice.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service  // Indica que esta classe é um serviço gerenciado pelo Spring.
public class TokenService {
    @Value("${api.security.token.secret}")  // Injeta o valor da propriedade 'api.security.token.secret' no campo 'secret'.
    private String secret;  // Declaração da variável que armazenará o segredo usado para assinar os tokens JWT.

    // Método público para gerar um token JWT para um usuário específico.
    public String generateToken(User user){
        try {  // Início do bloco try para capturar exceções durante a geração do token.
            Algorithm algorithm = Algorithm.HMAC256(secret);  // Cria uma instância do algoritmo HMAC256 usando o segredo.

            return JWT.create()  // Inicia a criação do token JWT.
                    .withIssuer("walletwizard_service")  // Define o emissor do token.
                    .withSubject(user.getLogin())  // Define o sujeito do token como o login do usuário.
                    .withExpiresAt(genExpirationDate())  // Define a data de expiração do token.
                    .sign(algorithm);  // Retorna o token JWT gerado.
        } catch (JWTCreationException exception) {  // Captura exceções que possam ocorrer durante a criação do token.
            throw new RuntimeException("Erro enquanto gera o Token", exception);  // Lança uma nova exceção com a mensagem de erro e a exceção original.
        }
    }

    // Método público para validar um token JWT e retornar o sujeito (subject) do token.
    public String validateToken(String token) {
        try {  // Início do bloco try para capturar exceções durante a validação do token.
            Algorithm algorithm = Algorithm.HMAC256(secret);  // Cria uma instância do algoritmo HMAC256 usando o segredo.
            return JWT.require(algorithm)  // Inicia o processo de validação do token.
                    .withIssuer("walletwizard_service")  // Verifica se o emissor do token é "walletwizard_service".
                    .build()  // Constrói o verificador do token.
                    .verify(token)  // Verifica a assinatura e a validade do token.
                    .getSubject();  // Retorna o sujeito (subject) do token se a verificação for bem-sucedida.
        } catch (JWTVerificationException exception) {  // Captura exceções que possam ocorrer durante a validação do token.
            return "";  // Retorna uma string vazia se a validação falhar.
        }
    }

    // Método privado para gerar uma data de expiração para o token.
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));  // Retorna um Instant que representa a data e hora atual mais 2 horas, com o fuso horário UTC-3.
    }
}
