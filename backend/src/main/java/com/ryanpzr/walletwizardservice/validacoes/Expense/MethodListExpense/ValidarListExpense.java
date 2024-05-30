package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodListExpense;

import com.ryanpzr.walletwizardservice.model.expense.Expense;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ValidarListExpense {

    void validar(List<Expense> expensesPage);

}
