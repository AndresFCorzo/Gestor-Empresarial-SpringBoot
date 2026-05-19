package com.gestorempresarial.model;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroFactura;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String estado = "PENDIENTE";
    private Double subtotal = 0.0;
    private Double totalIva = 0.0;
    private Double total = 0.0;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleFactura> detalles = new ArrayList<>();
    
    public Factura() {
        this.fecha = new Date();
    }
    
    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
        calcularTotales();
    }
    
    public void calcularTotales() {
        subtotal = 0.0;
        totalIva = 0.0;
        total = 0.0;
        for (DetalleFactura d : detalles) {
            subtotal += d.getSubtotal();
            totalIva += d.getValorIva();
            total += d.getTotal();
        }
    }
    
    public void emitir() {
        this.estado = "EMITIDA";
    }
    
    public void anular() {
        this.estado = "ANULADA";
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    public Double getTotalIva() { return totalIva; }
    public void setTotalIva(Double totalIva) { this.totalIva = totalIva; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }
}