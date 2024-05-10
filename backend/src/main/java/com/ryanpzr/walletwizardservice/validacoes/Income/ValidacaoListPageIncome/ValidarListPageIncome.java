package com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoListPageIncome;

import com.ryanpzr.walletwizardservice.exceptions.ListIsNullException;
import com.ryanpzr.walletwizardservice.model.income.Income;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarListPageIncome implements ValidacaoListPageIncome{

    public void validar(Page<Income> list) throws ListIsNullException {
        if (list.isEmpty()) {
            throw new ListIsNullException("Não a registros de receita!");
        }

    }

}
