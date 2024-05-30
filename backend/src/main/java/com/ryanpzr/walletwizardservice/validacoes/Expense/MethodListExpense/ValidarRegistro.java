package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodListExpense;

import com.ryanpzr.walletwizardservice.model.expense.Expense;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarRegistro implements ValidarListExpense{

    public void validar(List<Expense> expensesPage) {
        if (expensesPage.isEmpty()) {
            throw new EntityNotFoundException("Nenhum registro encontrado!");
        }
    }
}
