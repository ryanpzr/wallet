package com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoListPageIncome;

import com.ryanpzr.walletwizardservice.exceptions.ListIsNullException;
import com.ryanpzr.walletwizardservice.model.income.Income;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ValidacaoListPageIncome {

    void validar(Page<Income> list) throws ListIsNullException;

}
