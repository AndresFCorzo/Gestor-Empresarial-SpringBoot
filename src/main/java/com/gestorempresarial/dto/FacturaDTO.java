/**
 * DTO para transferencia de datos de Factura
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.dto;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class FacturaDTO {
    
    private Long idFactura;
    
    @NotBlank(message = "El número de factura es obligatorio")
    private String numeroFactura;
    
    private Date fecha;
    
    private String estado = "PENDIENTE";
    
    private Double subtotal = 0.0;
    private Double totalIva = 0.0;
    private Double total = 0.0;
    
    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;
    
    private String nombreCliente;
    
    @NotEmpty(message = "La factura debe tener al menos un producto")
    private List<DetalleFacturaDTO> detalles;
    
    // Constructores
    public FacturaDTO() {
        this.fecha = new Date();
    }
    
    public FacturaDTO(String numeroFactura, Long idCliente, List<DetalleFacturaDTO> detalles) {
        this();
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.detalles = detalles;
    }
    
    // Getters y Setters
    public Long getIdFactura() { return idFactura; }
    public void setIdFactura(Long idFactura) { this.idFactura = idFactura; }
    
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
    
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    
    public List<DetalleFacturaDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFacturaDTO> detalles) { this.detalles = detalles; }
    
    // ============================================
    // CLASE INTERNA DetalleFacturaDTO (COMPLETA)
    // ============================================
    public static class DetalleFacturaDTO {
        
        @NotNull(message = "El producto es obligatorio")
        private Long idProducto;
        
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        private Integer cantidad;
        
        private String nombreProducto;
        private Double precioUnitario;
        private Double subtotal;
        private Double valorIva;
        private Double total;
        
        // Constructores
        public DetalleFacturaDTO() {}
        
        public DetalleFacturaDTO(Long idProducto, Integer cantidad) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
        }
        
        // Getters y Setters
        public Long getIdProducto() { return idProducto; }
        public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
        
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        
        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
        
        public Double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
        
        public Double getSubtotal() { return subtotal; }
        public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
        
        public Double getValorIva() { return valorIva; }
        public void setValorIva(Double valorIva) { this.valorIva = valorIva; }
        
        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }
}