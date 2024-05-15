package com.ryanpzr.walletwizardservice.infra.service;

import com.ryanpzr.walletwizardservice.exceptions.ListIsNullException;
import com.ryanpzr.walletwizardservice.model.income.Income;
import com.ryanpzr.walletwizardservice.model.income.IncomeDTO;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoFindByMes.ValidacaoFindByMes;
import com.ryanpzr.walletwizardservice.validacoes.Income.ValidacaoListPageIncome.ValidacaoListPageIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private ValidacaoFindByMes validacaoFindByMes;

    @Autowired
    private ValidacaoListPageIncome validacaoListPageIncome;

    // Insere uma receita a tabela Income
    public Income insertIncome(IncomeDTO incomeDTO) {
        try {
            validacaoFindByMes.validar(incomeDTO);
            return repository.save(new Income(incomeDTO));

        } catch (RuntimeException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Lista todas as receitras cadastradas na tabela Income
    public Page<Income> listPageIncome(Pageable pageable) throws ListIsNullException {
        Page<Income> list = repository.findAll(pageable);
        validacaoListPageIncome.validar(list);

        return list;
    }
}
