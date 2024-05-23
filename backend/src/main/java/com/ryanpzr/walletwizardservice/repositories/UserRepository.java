package com.ryanpzr.walletwizardservice.repositories;

import com.ryanpzr.walletwizardservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    // Interface que realiza as QUERY personalizadas no banco de dados
    UserDetails findByLogin(String login);

}
