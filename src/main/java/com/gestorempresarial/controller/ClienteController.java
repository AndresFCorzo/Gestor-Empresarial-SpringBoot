package com.gestorempresarial.controller;

import com.gestorempresarial.dto.ClienteDTO;
import com.gestorempresarial.model.Cliente;
import com.gestorempresarial.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(dto.getNombre());
            cliente.setNit(dto.getNit());
            cliente.setDireccion(dto.getDireccion());
            cliente.setEmail(dto.getEmail());
            cliente.setTelefono(dto.getTelefono());
            
            Cliente nuevo = clienteService.registrar(cliente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Cliente registrado exitosamente");
            response.put("cliente", nuevo);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(dto.getNombre());
            cliente.setNit(dto.getNit());
            cliente.setDireccion(dto.getDireccion());
            cliente.setEmail(dto.getEmail());
            cliente.setTelefono(dto.getTelefono());
            
            Cliente actualizado = clienteService.actualizar(id, cliente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Cliente actualizado exitosamente");
            response.put("cliente", actualizado);
            
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
            clienteService.eliminar(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Cliente eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}