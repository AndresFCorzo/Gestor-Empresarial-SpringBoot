package com.gestorempresarial.controller;

import com.gestorempresarial.dto.FacturaDTO;
import com.gestorempresarial.model.Cliente;
import com.gestorempresarial.model.DetalleFactura;
import com.gestorempresarial.model.Factura;
import com.gestorempresarial.model.Producto;
import com.gestorempresarial.service.ClienteService;
import com.gestorempresarial.service.FacturaService;
import com.gestorempresarial.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {
    
    @Autowired
    private FacturaService facturaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Factura>> listar() {
        return ResponseEntity.ok(facturaService.listar());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facturaService.buscarPorId(id));
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> emitir(@Valid @RequestBody FacturaDTO dto) {
        try {
            Cliente cliente = clienteService.buscarPorId(dto.getClienteId());
            
            Factura factura = new Factura();
            factura.setNumeroFactura(dto.getNumeroFactura());
            factura.setCliente(cliente);
            
            for (FacturaDTO.DetalleFacturaDTO detDTO : dto.getDetalles()) {
                Producto producto = productoService.buscarPorId(detDTO.getProductoId());
                DetalleFactura detalle = new DetalleFactura(producto, detDTO.getCantidad());
                factura.agregarDetalle(detalle);
            }
            
            Factura nueva = facturaService.emitir(factura);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura emitida exitosamente");
            response.put("factura", nueva);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            facturaService.anular(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Factura anulada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping("/reporte/ventas")
    public ResponseEntity<?> reporteVentas(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date fin) {
        
        List<Factura> facturas = facturaService.reporteVentas(inicio, fin);
        
        Double totalGeneral = facturas.stream()
            .filter(f -> "EMITIDA".equals(f.getEstado()))
            .mapToDouble(Factura::getTotal)
            .sum();
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("periodo", Map.of("inicio", inicio, "fin", fin));
        reporte.put("facturas", facturas);
        reporte.put("totalFacturas", facturas.size());
        reporte.put("totalGeneral", totalGeneral);
        
        return ResponseEntity.ok(reporte);
    }
}