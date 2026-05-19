package com.gestorempresarial.model;

import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
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
    
    public Double getPrecioConIva() {
        if (aplicaIva) {
            return precio + (precio * porcentajeIva / 100);
        }
        return precio;
    }
}