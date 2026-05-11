/**
 * Clase que representa un producto del inventario
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9-]{3,20}$", message = "Código inválido (3-20 caracteres alfanuméricos)")
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private Double precio;
    
    private boolean aplicaIva = true;
    
    @DecimalMin(value = "0", message = "El porcentaje de IVA debe ser mayor o igual a cero")
    private Double porcentajeIva = 19.0;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock = 0;
    
    private String categoria;
    
    // Constructor
    public Producto() {}
    
    public Producto(String nombre, String codigo, Double precio, boolean aplicaIva, 
                    Double porcentajeIva, Integer stock, String categoria) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.aplicaIva = aplicaIva;
        this.porcentajeIva = aplicaIva ? porcentajeIva : 0;
        this.stock = stock;
        this.categoria = categoria;
    }
    
    // Método para calcular precio con IVA
    public Double calcularPrecioConIva() {
        if (aplicaIva) {
            return precio + (precio * porcentajeIva / 100);
        }
        return precio;
    }
    
    // Getters y Setters
    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
    
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