package com.ryanpzr.walletwizardservice.model.expense;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ryanpzr.walletwizardservice.model.income.Income;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Entidade que cria a tabela no banco de dados e recebe os parâmetros do DTO
@Entity(name = "Expense")
@Table(name = "expenses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompra;
    private Double valorCompra;
    private String descricao;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_id")
    private Income income;

    // Recebe os parâmetros do DTO -> ExpenseDTO
    public Expense(ExpenseDTO expensesDTO) {
        this.nomeCompra = expensesDTO.nomeCompra();
        this.valorCompra = expensesDTO.valorCompra();
        this.descricao = expensesDTO.descricao();
        this.date = expensesDTO.date();
        this.category = expensesDTO.category();
        this.income = new Income(expensesDTO.incomeId());
    }
}
