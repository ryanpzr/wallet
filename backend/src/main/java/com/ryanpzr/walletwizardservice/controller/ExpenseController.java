package com.ryanpzr.walletwizardservice.controller;

import com.ryanpzr.walletwizardservice.exceptions.NomeIgualException;
import com.ryanpzr.walletwizardservice.exceptions.ReceitaExpiradaException;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.infra.service.ExpenseService;
import com.ryanpzr.walletwizardservice.repositories.IncomeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    //Lista todos os gastos
    @GetMapping("/list")
    public ResponseEntity<Page<Expense>> listExpense(Pageable pageable){
        Page<Expense> listExpense = service.listExpense(pageable);
        return ResponseEntity.ok().body(listExpense);
    }

    //Lista os gastos filtrados pelo mês
    @GetMapping("/listMonth/{dateParam}")
    public ResponseEntity<Page<Expense>> listExpenseMonth(@PathVariable("dateParam") String dateParam, Pageable pageable){
        Page<Expense> listExpense = service.listExpenseMonth(dateParam, pageable);
        return ResponseEntity.ok().body(listExpense);
    }

    //Posta um novo gasto
    @PostMapping
    public ResponseEntity<Expense> InsertExpense(@RequestBody ExpenseDTO expensesDTO) throws ReceitaExpiradaException, NomeIgualException {
        Expense data = service.insertData(expensesDTO);
        return ResponseEntity.ok().body(data);
    }

    //Deleta um gasto pelo nome da compra e pelo mês
    @DeleteMapping("/delete/{nomeDaCompra}/{mes}")
    @Transactional
    public ResponseEntity<Expense> deleteExpense(@PathVariable("nomeDaCompra") String nomeDaCompra, @PathVariable("mes") String mes) {
        Expense data = service.deleteExpense(nomeDaCompra, mes);
        return ResponseEntity.ok().body(data);
    }
}
