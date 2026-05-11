/**
 * Clase principal de la aplicación Spring Boot
 * 
 * @author Andres Felipe Corzo Angarita
 * @version 1.0
 */
package com.gestorempresarial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorEmpresarialApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GestorEmpresarialApplication.class, args);
        System.out.println("=== GESTOR EMPRESARIAL INTEGRADO ===");
        System.out.println("Aplicación iniciada correctamente");
        System.out.println("Accede a: http://localhost:8080");
    }
}