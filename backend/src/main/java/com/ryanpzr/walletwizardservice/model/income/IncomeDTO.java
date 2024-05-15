package com.ryanpzr.walletwizardservice.model.income;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record IncomeDTO(
        // DTO que recebe os dados da receita a ser cadastrada no banco de dados e realiza as validações necessárias
        @NotBlank(message = "Nome da Empresa em que você trabalha não pode ser nulo ou vazio")
        @Pattern(regexp = "[\\p{L}\\s]+", message = "Nome da Empresa em que você trabalha deve conter apenas letras e espaços")
        String nomeEmpresa,

        String mes,

        @NotNull(message = "Valor da receita não pode ser nulo")
        @Positive(message = "Valor da receita deve ser um número positivo")
        Integer receita,

        Double totalGasto
) {
}
