package com.ryanpzr.walletwizardservice.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Classe que configura a segurança da aplicação que é feita com Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    // Configura as permissões de cada role
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Desabilita a proteção CSRF
                .csrf(csrf -> csrf.disable())
                // Define a política de gerenciamento de sessão como sem estado (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura as autorizações para os diferentes endpoints HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso sem autenticação aos endpoints de login e registro
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        // Permite acesso apenas para usuários com a função de ADMIN aos endpoints de criação de renda
                        .requestMatchers(HttpMethod.POST, "/api/income").hasRole("ADMIN")
                        // Define que todos os outros endpoints requerem autenticação
                        .anyRequest().authenticated()
                )
                // Adiciona o filtro de segurança personalizado antes do filtro padrão de autenticação por nome de usuário e senha
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // Constrói e retorna a cadeia de filtros de segurança configurada
                .build();
    }

    // Configura e retorna o gerenciador de autenticação personalizado, utilizando a configuração de autenticação fornecida.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Cryptografa a senha passada na hora do registro na aplicação
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
