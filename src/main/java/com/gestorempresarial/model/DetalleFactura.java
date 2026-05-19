package com.gestorempresarial.model;

import javax.persistence.*;

@Entity
@Table(name = "detalles_factura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Double valorIva;
    private Double total;
    
    public DetalleFactura() {}
    
    public DetalleFactura(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        calcularValores();
    }
    
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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