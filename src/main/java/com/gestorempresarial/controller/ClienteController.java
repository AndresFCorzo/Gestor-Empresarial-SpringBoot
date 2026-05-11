/**
 * Controlador REST para la gestión de clientes
 * Endpoints: /api/clientes
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.controller;

import com.gestorempresarial.modelo.Cliente;
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
    
    /**
     * Obtiene todos los clientes
     * GET /api/clientes
     * @return Lista de clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Obtiene un cliente por su ID
     * GET /api/clientes/{id}
     * @param id ID del cliente
     * @return Cliente encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }
    
    /**
     * Registra un nuevo cliente
     * POST /api/clientes
     * @param cliente Datos del cliente
     * @return Cliente registrado
     */
    @PostMapping
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.registrarCliente(cliente);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Cliente registrado exitosamente");
            response.put("cliente", nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Actualiza un cliente existente
     * PUT /api/clientes/{id}
     * @param id ID del cliente
     * @param cliente Datos actualizados
     * @return Cliente actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Cliente actualizado exitosamente");
            response.put("cliente", clienteActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Elimina un cliente
     * DELETE /api/clientes/{id}
     * @param id ID del cliente
     * @return Mensaje de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Cliente eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Busca clientes por NIT
     * GET /api/clientes/buscar/nit?nit=...
     * @param nit NIT del cliente
     * @return Cliente encontrado
     */
    @GetMapping("/buscar/nit")
    public ResponseEntity<?> buscarPorNit(@RequestParam String nit) {
        return clienteService.buscarPorNit(nit)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Busca clientes por nombre
     * GET /api/clientes/buscar/nombre?nombre=...
     * @param nombre Nombre a buscar
     * @return Lista de clientes
     */
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Cliente>> buscarPorNombre(@RequestParam String nombre) {
        List<Cliente> clientes = clienteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(clientes);
    }
}