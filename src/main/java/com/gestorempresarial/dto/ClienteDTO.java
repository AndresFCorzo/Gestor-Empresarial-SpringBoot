package com.gestorempresarial.dto;

import javax.validation.constraints.*;

public class ClienteDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "^[0-9]{1,10}-[0-9]$", message = "Formato de NIT inválido. Ejemplo: 900123456-1")
    private String nit;
    
    private String direccion;
    
    @Email(message = "El correo debe ser válido")
    private String email;
    
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}