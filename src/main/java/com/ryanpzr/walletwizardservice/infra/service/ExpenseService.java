package com.ryanpzr.walletwizardservice.infra.service;

import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.repositories.ExpensesRepository;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodInsertDataExpense.ValidarInsertDataExpense;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodListExpense.ValidarListExpense;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodlistExpenseMonth.ValidarListExpenseMonth;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpensesRepository repository;

    @Autowired
    private IncomeRepository IncomeRepository;

    @Autowired
    private List<ValidarInsertDataExpense> validarInsertDataExpenses;

    @Autowired
    private ValidarListExpenseMonth validarListExpenseMonthList;

    @Autowired
    private ValidarListExpense validarListExpense;

    public Expense insertData(ExpenseDTO expensesDTO) {

        validarInsertDataExpenses.forEach(v -> v.validar(expensesDTO));

        try {
            String nomeMes = converterMes(expensesDTO.date());
            IncomeRepository.atualizarTotal(expensesDTO.valorCompra(), nomeMes);

            return repository.save(new Expense(expensesDTO));

        } catch (Exception ex) {
            throw new EntityNotFoundException("Dados digitados incorretamente, tente novamente!" + ex);

        }
    }

    private String converterMes(LocalDate date) {
        int mes = date.getMonthValue();
        return Month.of(mes).getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public Page<Expense> listExpenseMonth(String dateParam, Pageable pageable) {
        try {
            Page<Expense> expensesPage = repository.listarPageWithYear(dateParam, pageable);
            validarListExpenseMonthList.validar(expensesPage);

            return expensesPage;
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de data inválido: " + dateParam);

        }
    }

    public Page<Expense> listExpense(Pageable pageable) {
        Page<Expense> expensesPage = repository.findAll(pageable);
        validarListExpense.validar(expensesPage);

        return expensesPage;
    }

    public Expense deleteExpense(String nomeDaCompra, String mesParam) {
        try {
            Optional<Expense> paramNotFound = repository.findByNomeCompra(nomeDaCompra);
            if (paramNotFound.isEmpty()) {
                throw new EntityNotFoundException("Registro não encontrado!");
            }

            repository.atualizarTotalIncome(nomeDaCompra, mesParam);
            repository.deletarGasto(nomeDaCompra);

        } catch (AopInvocationException ex) {
            throw new EntityNotFoundException("Registro não encontrado!");

        }
        return null;

    }
}
