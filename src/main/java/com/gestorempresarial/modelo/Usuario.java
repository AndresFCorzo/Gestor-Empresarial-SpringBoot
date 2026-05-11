/**
 * Clase que representa un usuario del sistema
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Column(unique = true, nullable = false)
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;
    
    @NotBlank(message = "El rol es obligatorio")
    private String rol;
    
    private String documentoIdentidad;
    private String telefono;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcceso;
    
    private boolean activo = true;
    
    // Constructores
    public Usuario() {
        this.fechaRegistro = new Date();
    }
    
    public Usuario(String nombre, String correo, String contrasena, String rol) {
        this();
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Date getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(Date ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}