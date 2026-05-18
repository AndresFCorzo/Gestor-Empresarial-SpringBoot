/**
 * DTO para solicitud de login
 * Soporta tanto username como correo
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.dto;

import javax.validation.constraints.*;

public class LoginRequestDTO {
    
    @NotBlank(message = "El usuario/correo es obligatorio")
    private String username;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;
    
    // Constructores
    public LoginRequestDTO() {}
    
    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    // Método para compatibilidad con correo
    public String getCorreo() { return username; }
    public void setCorreo(String correo) { this.username = correo; }
}