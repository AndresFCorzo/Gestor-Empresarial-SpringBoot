/**
 * DTO para transferencia de datos de Cliente
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.dto;

import javax.validation.constraints.*;
import java.util.Date;

public class ClienteDTO {
    
    private Long idCliente;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "^[0-9]{1,10}-[0-9]$", message = "Formato de NIT inválido. Ejemplo: 900123456-1")
    private String nit;
    
    private String direccion;
    
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;
    
    private Date fechaRegistro;
    
    // Constructores
    public ClienteDTO() {}
    
    public ClienteDTO(Long idCliente, String nombre, String nit, String direccion, String correo, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaRegistro = new Date();
    }
    
    // Getters y Setters
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}