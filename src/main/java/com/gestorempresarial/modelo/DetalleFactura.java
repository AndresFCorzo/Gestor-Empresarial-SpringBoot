/**
 * Clase que representa el detalle de una factura
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.modelo;

import javax.persistence.*;

@Entity
@Table(name = "detalles_factura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;
    
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;
    
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
    
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Double valorIva;
    private Double total;
    
    // Constructores
    public DetalleFactura() {}
    
    public DetalleFactura(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        calcularValores();
    }
    
    // Método para calcular valores
    public void calcularValores() {
        this.subtotal = cantidad * precioUnitario;
        if (producto != null && producto.isAplicaIva()) {
            this.valorIva = subtotal * producto.getPorcentajeIva() / 100;
        } else {
            this.valorIva = 0.0;
        }
        this.total = subtotal + valorIva;
    }
    
    // Getters y Setters
    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }
    
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto;
        this.precioUnitario = producto.getPrecio();
        calcularValores();
    }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad;
        calcularValores();
    }
    
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { 
        this.precioUnitario = precioUnitario;
        calcularValores();
    }
    
    public Double getSubtotal() { return subtotal; }
    public Double getValorIva() { return valorIva; }
    public Double getTotal() { return total; }
}