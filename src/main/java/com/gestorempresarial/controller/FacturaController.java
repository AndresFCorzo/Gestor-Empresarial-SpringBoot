/**
 * Controlador REST para la gestión de facturación
 * Endpoints: /api/facturas
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.controller;

import com.gestorempresarial.modelo.Factura;
import com.gestorempresarial.service.FacturaService;
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
    
    /**
     * Obtiene todas las facturas
     * GET /api/facturas
     */
    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas() {
        return ResponseEntity.ok(facturaService.listarFacturas());
    }
    
    /**
     * Obtiene una factura por ID
     * GET /api/facturas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.buscarPorId(id));
    }
    
    /**
     * Emite una nueva factura (HU-01)
     * POST /api/facturas
     */
    @PostMapping
    public ResponseEntity<?> emitirFactura(@Valid @RequestBody Factura factura) {
        try {
            Factura nuevaFactura = facturaService.emitirFactura(factura);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura emitida exitosamente");
            response.put("factura", nuevaFactura);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Anula una factura
     * PUT /api/facturas/{id}/anular
     */
    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anularFactura(@PathVariable Long id) {
        try {
            facturaService.anularFactura(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Factura anulada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Obtiene facturas por cliente
     * GET /api/facturas/cliente/{idCliente}
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Factura>> facturasPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(facturaService.listarFacturasPorCliente(idCliente));
    }
    
    /**
     * Obtiene facturas por estado
     * GET /api/facturas/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Factura>> facturasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(facturaService.listarFacturasPorEstado(estado));
    }
    
    /**
     * Genera reporte de ventas por período (HU-06)
     * GET /api/facturas/reporte/ventas?inicio=dd/MM/yyyy&fin=dd/MM/yyyy
     */
    @GetMapping("/reporte/ventas")
    public ResponseEntity<?> generarReporteVentas(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date fin) {
        
        List<Factura> facturas = facturaService.generarReporteVentas(inicio, fin);
        
        // Calcular totales del reporte
        Double totalGeneral = facturas.stream()
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