package com.ryanpzr.walletwizardservice.model.expense;

import com.ryanpzr.walletwizardservice.model.income.Income;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ExpenseDTO(
        // DTO que recebe os dados do gasto a ser cadastrado ao banco de dados e realiza as validações necessárias
        @NotBlank(message = "Nome da compra não pode ser nulo ou vazio")
        @Pattern(regexp = "[\\p{L}\\s]+", message = "Nome da compra deve conter apenas letras e espaços")
        String nomecompra,

        @NotNull(message = "Valor da compra não pode ser nulo")
        @Positive(message = "Valor da compra deve ser um número positivo")
        Double valorcompra,

        @NotBlank(message = "Descrição da compra não pode ser nulo ou vazio")
        String descricao,

        @NotNull(message = "Data não pode ser nulo")
        String date,

        @NotNull(message = "Categoria não pode ser nula")
        Category category,

        Long income_id
) {
}
