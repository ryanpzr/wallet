package com.ryanpzr.walletwizardservice.model.income;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Income")
@Table(name = "Income")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeEmpresa;
    private String mes;
    private Integer receita;
    private Double total;

    public Income(IncomeDTO incomeDTO) {
        this.nomeEmpresa = incomeDTO.nomeEmpresa();
        this.mes = incomeDTO.mes();
        this.receita = incomeDTO.receita();
        this.total = incomeDTO.totalGasto();
    }

    public Income(Long incomeId) {
        this.id = incomeId;
    }
}
