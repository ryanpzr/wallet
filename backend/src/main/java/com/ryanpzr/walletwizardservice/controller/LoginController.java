package com.ryanpzr.walletwizardservice.controller;

import com.ryanpzr.walletwizardservice.model.user.*;
import com.ryanpzr.walletwizardservice.repositories.UserRepository;
import com.ryanpzr.walletwizardservice.infra.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    //Faz login enviando o username e a senha, se tiver correto com oque está no bd, retorna um token
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid loginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    //Cria um usuario, é setado automaticamente uma role USER. Para ter a role ADM, é preciso inserir diretamente pelo bd
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid registerDTO data) {
        if (this.repository.findByLogin(data.register()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserRole userRole = UserRole.USER;
        User newUser = new User(data.register(), encryptedPassword, userRole);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
