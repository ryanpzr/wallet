package com.ryanpzr.walletwizardservice.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Configura o CORS da aplicação, liberando as requisições do tipo GET, POST, PUT e DELETE
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://192.168.25.4:8000",
                        "http://26.59.75.250:8000",
                        "http://172.22.64.1:8000",
                        "http://127.0.0.1:8000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
