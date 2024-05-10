package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodlistExpenseMonth;

import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.repositories.ExpensesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class InvalidFormatData implements ValidarListExpenseMonth{

    @Autowired
    private ExpensesRepository repository;

    public void validar(Page<Expense> expensesPage) {

        if (expensesPage.isEmpty()) {
            throw new EntityNotFoundException("Nenhum registro encontrado para o mês selecionado!");
        }
    }

}
