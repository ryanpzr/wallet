package com.ryanpzr.walletwizardservice.controller;

import com.ryanpzr.walletwizardservice.exceptions.NomeIgualException;
import com.ryanpzr.walletwizardservice.exceptions.ReceitaExpiradaException;
import com.ryanpzr.walletwizardservice.model.expense.Expense;
import com.ryanpzr.walletwizardservice.model.expense.ExpenseDTO;
import com.ryanpzr.walletwizardservice.infra.service.ExpenseService;
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

    @GetMapping("/list")
    public ResponseEntity<Page<Expense>> listExpense(Pageable pageable){
        Page<Expense> listExpense = service.listExpense(pageable);
        return ResponseEntity.ok().body(listExpense);
    }

    @GetMapping("/listMonth")
    public ResponseEntity<Page<Expense>> listExpenseMonth(@RequestParam(required = false) String dateParam, Pageable pageable){
        Page<Expense> listExpense = service.listExpenseMonth(dateParam, pageable);
        return ResponseEntity.ok().body(listExpense);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Expense> InsertExpense(@RequestBody @Valid ExpenseDTO expensesDTO) throws ReceitaExpiradaException, NomeIgualException {
        Expense data = service.insertData(expensesDTO);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Expense> deleteExpense(@RequestParam String nomeDaCompra, String mes) {
        Expense data = service.deleteExpense(nomeDaCompra, mes);
        return ResponseEntity.ok().body(data);
    }
}
