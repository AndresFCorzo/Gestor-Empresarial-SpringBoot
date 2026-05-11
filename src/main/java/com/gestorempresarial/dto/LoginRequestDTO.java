/**
 * DTO para solicitud de login
 * @author Andrés Felipe Corzo Angarita
 */
package com.gestorempresarial.dto;

import javax.validation.constraints.*;

public class LoginRequestDTO {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String contrasena;
    
    // Constructores
    public LoginRequestDTO() {}
    
    public LoginRequestDTO(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    // Getters y Setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}