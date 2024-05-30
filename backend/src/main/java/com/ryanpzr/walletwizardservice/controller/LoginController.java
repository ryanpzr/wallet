package com.ryanpzr.walletwizardservice.controller;

import com.ryanpzr.walletwizardservice.infra.service.UserService;
import com.ryanpzr.walletwizardservice.model.user.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    //Faz login enviando o username e a senha, se tiver correto com oque está no bd, retorna um token
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid loginDTO data){
        String token = userService.returnToken(data.login(), data.password());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    //Cria um usuario, é setado automaticamente uma role USER. Para ter a role ADM, é preciso inserir diretamente pelo bd
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid registerDTO data) {
        userService.registerUser(data.register(), data.password());
        return ResponseEntity.ok().build();
    }

}
