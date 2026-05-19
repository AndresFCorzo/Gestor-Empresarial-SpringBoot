package com.gestorempresarial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GestorEmpresarialApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GestorEmpresarialApplication.class, args);
        System.out.println("========================================");
        System.out.println("  GESTOR EMPRESARIAL INTEGRADO");
        System.out.println("  Aplicación iniciada correctamente");
        System.out.println("  API disponible en: http://localhost:8080");
        System.out.println("========================================");
    }
}