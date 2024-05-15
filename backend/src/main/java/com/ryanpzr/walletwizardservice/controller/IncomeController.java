package com.ryanpzr.walletwizardservice.controller;

import com.ryanpzr.walletwizardservice.exceptions.ListIsNullException;
import com.ryanpzr.walletwizardservice.model.income.Income;
import com.ryanpzr.walletwizardservice.model.income.IncomeDTO;
import com.ryanpzr.walletwizardservice.infra.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService service;

    //Lista todas os meses/receitas
    @GetMapping
    public ResponseEntity<Page<Income>> listIncome(Pageable pageable) throws ListIsNullException {
        Page<Income> data = service.listPageIncome(pageable);
        return ResponseEntity.ok().body(data);
    }

    //Posta uma nova receita (APENAS USUARIOS COM A ROLE ADM PODE INSERIR UM NOVO DADO)
    @PostMapping
    public ResponseEntity<Income> insertIncome(@RequestBody @Valid IncomeDTO incomeDTO){
        Income data = service.insertIncome(incomeDTO);
        return ResponseEntity.ok().body(data);
    }

}
