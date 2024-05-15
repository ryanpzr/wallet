package com.ryanpzr.walletwizardservice.infra.security;

import com.ryanpzr.walletwizardservice.repositories.UserRepository;
import com.ryanpzr.walletwizardservice.infra.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    //Sobrescreve o metodo doFilterInternal para realizar uma lógica de filtragem personalizada
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token da requisição
        var token = this.recoverToken(request);
        // Valida de o token passado não esta nulo, valida o token e obtém os detalhes do usuário
        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);

            // Cria uma autenticação baseada no nome de usuário e senha, utilizando os detalhes do usuário obtidos
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            // Define a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continua a execução da cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // Se for passado um token na parte do "Bearer", usa o método replace para retirar essa parte e vir apenas o token
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
