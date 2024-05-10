package com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoFindByMes;

import com.ryanpzr.walletwizardservice.model.income.IncomeDTO;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarReceitaJaCadastrada implements ValidacaoFindByMes {

    @Autowired
    private IncomeRepository repository;

    public void validar(IncomeDTO incomeDTO) {
        String mes = repository.findByMes(incomeDTO.mes());
        if (incomeDTO.mes().equals(mes)) {
            throw new EntityNotFoundException("Não é possivel cadastrar uma receita com o mesmo mês já cadastrado!");
        }
    }

}
