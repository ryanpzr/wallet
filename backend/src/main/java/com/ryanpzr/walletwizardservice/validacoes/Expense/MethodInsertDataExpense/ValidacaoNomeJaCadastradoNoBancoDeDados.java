package com.ryanpzr.walletwizardservice.validacoes.Expense.MethodInsertDataExpense;

import com.ryanpzr.walletwizardservice.exceptions.NomeIgualException;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidacaoNomeJaCadastradoNoBancoDeDados implements ValidarInsertDataExpense {

    @Autowired
    private ExpensesRepository repository;

    public void validar(ExpenseDTO dto){

        Optional<Expense> existeCompra = repository.findByNomeCompra(dto.nomeCompra());
        if (existeCompra.isPresent()) {
            try {
                throw new NomeIgualException("Não é possivel cadastrar um gasto com o mesmo nome no banco de dados, tente novamente!");
            } catch (NomeIgualException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
