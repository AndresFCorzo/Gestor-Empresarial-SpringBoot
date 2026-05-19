package com.gestorempresarial.controller;

import com.gestorempresarial.dto.ProductoDTO;
import com.gestorempresarial.model.Producto;
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
    
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.buscarPorId(id));
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoDTO dto) {
        try {
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setCodigo(dto.getCodigo());
            producto.setPrecio(dto.getPrecio());
            producto.setAplicaIva(dto.isAplicaIva());
            producto.setPorcentajeIva(dto.getPorcentajeIva());
            producto.setStock(dto.getStock());
            producto.setCategoria(dto.getCategoria());
            
            Producto nuevo = productoService.registrar(producto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto registrado exitosamente");
            response.put("producto", nuevo);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        try {
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setCodigo(dto.getCodigo());
            producto.setPrecio(dto.getPrecio());
            producto.setAplicaIva(dto.isAplicaIva());
            producto.setPorcentajeIva(dto.getPorcentajeIva());
            producto.setStock(dto.getStock());
            producto.setCategoria(dto.getCategoria());
            
            Producto actualizado = productoService.actualizar(id, producto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto actualizado exitosamente");
            response.put("producto", actualizado);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}