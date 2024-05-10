package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodlistExpenseMonth;

import com.ryanpzr.walletwizardservice.model.expense.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ValidarListExpenseMonth {

    void validar(Page<Expense> expensesPage);

}
