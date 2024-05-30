package com.ryanpzr.walletwizardservice.infra.service;

import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.repositories.ExpensesRepository;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodInsertDataExpense.ValidarInsertDataExpense;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodListExpense.ValidarListExpense;
import com.ryanpzr.walletwizardservice.validacoes.Expense.MethodlistExpenseMonth.ValidarListExpenseMonth;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private IncomeRepository incomeRepository;

    @Autowired
    private List<ValidarInsertDataExpense> validarInsertDataExpenses;

    @Autowired
    private ValidarListExpenseMonth validarListExpenseMonthList;

    @Autowired
    private ValidarListExpense validarListExpense;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    // Insere um dado ao banco de dados e atualiza o total na tabela Income de acordo com o mês passado
    public Expense insertData(ExpenseDTO expensesDTO) {

        // Roda as validações
        validarInsertDataExpenses.forEach(v -> v.validar(expensesDTO));

        // Tenta realizar a operação, caso não dê, retorna uma exceção
        try {
            logger.info("Atualizando total para o mês: {}", expensesDTO.date());
            atualizarTotal(expensesDTO);
            logger.info("Salvando nova despesa");
            return repository.save(new Expense(expensesDTO));

        } catch (Exception ex) {
            logger.error("Erro ao inserir dados: ", ex);
            throw new EntityNotFoundException("Dados digitados incorretamente, tente novamente!" + ex);
        }
    }
    @Transactional
    private void atualizarTotal(ExpenseDTO expenseDTO) {
        String nomeMes = converterMes(expenseDTO.date());
        logger.info("Convertendo data {} para mês {}", expenseDTO.date(), nomeMes);
        incomeRepository.atualizarTotal(expenseDTO.valorCompra(), nomeMes);
    }

    // Retorna o mês passado em formato numérico em uma String do mês
    private String converterMes(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toLowerCase();
    }

    // Lista os gastos cadastrados de acordo com o mês passado
    public Page<Expense> listExpenseMonth(String dateParam, Pageable pageable) {
        try {
            Page<Expense> expensesPage = repository.listPageWithYear(dateParam, pageable);
            validarListExpenseMonthList.validar(expensesPage);

            return expensesPage;
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de data inválido: " + dateParam);

        }
    }

    // Lista todos os gastos cadastrados
    public Page<Expense> listExpense(Pageable pageable) {
        Page<Expense> expensesPage = repository.listAllPages(pageable);
        validarListExpense.validar(expensesPage);

        return expensesPage;
    }

    // Deleta um gasto de acordo com o nome e um mês passado como parametro
    public Expense deleteExpense(String nomeDaCompra, String mesParam) {
        try {
            // Verifica se o parâmetro retornado não é nulo, se for retorna uma exception
            Optional<Expense> paramNotFound = repository.findByNomeCompra(nomeDaCompra);
            if (paramNotFound.isEmpty()) {
                throw new EntityNotFoundException("Registro não encontrado!");
            }

            System.out.println(paramNotFound);

            // Atualiza o valor na tabela Income de acordo com o nome e o mês passado como parâmetro
            repository.atualizarTotalIncome(nomeDaCompra, mesParam);

            // Por último, o gasto é deletado da tabela Expense
            repository.deletarGasto(nomeDaCompra);

        } catch (AopInvocationException ex) {
            throw new EntityNotFoundException("Registro não encontrado!");

        }
        return null;

    }
}
