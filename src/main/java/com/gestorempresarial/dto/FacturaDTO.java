package com.gestorempresarial.dto;

import javax.validation.constraints.*;
import java.util.List;

public class FacturaDTO {
    
    @NotBlank(message = "El número de factura es obligatorio")
    private String numeroFactura;
    
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
    
    @NotEmpty(message = "La factura debe tener al menos un producto")
    private List<DetalleFacturaDTO> detalles;
    
    // Getters y Setters
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public List<DetalleFacturaDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFacturaDTO> detalles) { this.detalles = detalles; }
    
    public static class DetalleFacturaDTO {
        @NotNull(message = "El producto es obligatorio")
        private Long productoId;
        
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        private Integer cantidad;
        
        // Getters y Setters
        public Long getProductoId() { return productoId; }
        public void setProductoId(Long productoId) { this.productoId = productoId; }
        
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}