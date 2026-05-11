/**
 * Controlador REST para la gestión de productos
 * Endpoints: /api/productos
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.controller;

import com.gestorempresarial.modelo.Producto;
import com.gestorempresarial.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    /**
     * Obtiene todos los productos
     * GET /api/productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }
    
    /**
     * Obtiene un producto por ID
     * GET /api/productos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }
    
    /**
     * Registra un nuevo producto (HU-07)
     * POST /api/productos
     */
    @PostMapping
    public ResponseEntity<?> registrarProducto(@Valid @RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.registrarProducto(producto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto registrado exitosamente");
            response.put("producto", nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Actualiza un producto existente
     * PUT /api/productos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, producto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto actualizado exitosamente");
            response.put("producto", productoActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Actualiza el stock de un producto
     * PATCH /api/productos/{id}/stock?stock=...
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            productoService.actualizarStock(id, stock);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Stock actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Elimina un producto
     * DELETE /api/productos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Obtiene productos con stock bajo
     * GET /api/productos/stock-bajo?limite=5
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> productosStockBajo(@RequestParam(defaultValue = "5") Integer limite) {
        return ResponseEntity.ok(productoService.productosConStockBajo(limite));
    }
}