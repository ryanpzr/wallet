package com.ryanpzr.walletwizardservice.infra.service;

import com.ryanpzr.walletwizardservice.model.user.User;
import com.ryanpzr.walletwizardservice.model.user.UserRole;
import com.ryanpzr.walletwizardservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    public String returnToken(String login, String password) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void registerUser(String register, String password) {

        if (this.repository.findByLogin(register) != null) {
            ResponseEntity.badRequest().build();
            return;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        UserRole userRole = UserRole.USER;
        User newUser = new User(register, encryptedPassword, userRole);

        this.repository.save(newUser);

    }
}
