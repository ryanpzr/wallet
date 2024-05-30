package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodInsertDataExpense;

import com.ryanpzr.walletwizardservice.exceptions.ReceitaExpiradaException;
import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoParaUltrapassarReceita implements ValidarInsertDataExpense {

    @Autowired
    private IncomeRepository repository;

    public void validar(ExpenseDTO dto){

        if (dto.valorcompra() >= repository.findByReceita()) {
            try {
                throw new ReceitaExpiradaException("Você ira ultrapassar sua receita!");
            } catch (ReceitaExpiradaException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
