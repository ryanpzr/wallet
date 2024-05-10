package com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoFindByMes;

import com.ryanpzr.walletwizardservice.model.income.IncomeDTO;
import org.springframework.stereotype.Component;

@Component
public interface ValidacaoFindByMes {

    void validar(IncomeDTO incomeDTO);

}
