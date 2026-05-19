package com.gestorempresarial.dto;

import javax.validation.constraints.*;

public class ProductoDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9-]{3,20}$", message = "Código inválido (3-20 caracteres)")
    private String codigo;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private Double precio;
    
    private boolean aplicaIva = true;
    private Double porcentajeIva = 19.0;
    private Integer stock = 0;
    private String categoria;
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public boolean isAplicaIva() { return aplicaIva; }
    public void setAplicaIva(boolean aplicaIva) { this.aplicaIva = aplicaIva; }
    
    public Double getPorcentajeIva() { return porcentajeIva; }
    public void setPorcentajeIva(Double porcentajeIva) { this.porcentajeIva = porcentajeIva; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}