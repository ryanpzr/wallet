package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodInsertDataExpense;

import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import org.springframework.stereotype.Component;

@Component
public interface ValidarInsertDataExpense {

    void validar(ExpenseDTO dto);

}
