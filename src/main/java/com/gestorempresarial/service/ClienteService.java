/**
 * Servicio para la gestión de clientes
 * Implementa la lógica de negocio de la HU-01 y HU-02
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.service;

import com.gestorempresarial.modelo.Cliente;
import com.gestorempresarial.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    /**
     * Registra un nuevo cliente en el sistema (HU-01)
     * @param cliente Objeto Cliente a registrar
     * @return Cliente registrado con su ID
     * @throws RuntimeException si el NIT ya existe
     */
    @Transactional
    public Cliente registrarCliente(Cliente cliente) {
        // Validar que el NIT no exista
        if (clienteRepository.existsByNit(cliente.getNit())) {
            throw new RuntimeException("Ya existe un cliente con el NIT: " + cliente.getNit());
        }
        return clienteRepository.save(cliente);
    }
    
    /**
     * Obtiene todos los clientes
     * @return Lista de clientes
     */
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    
    /**
     * Busca un cliente por su ID
     * @param id ID del cliente
     * @return Cliente encontrado
     * @throws RuntimeException si no existe
     */
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }
    
    /**
     * Busca un cliente por su NIT
     * @param nit NIT del cliente
     * @return Cliente encontrado
     */
    public Optional<Cliente> buscarPorNit(String nit) {
        return clienteRepository.findByNit(nit);
    }
    
    /**
     * Actualiza los datos de un cliente existente
     * @param id ID del cliente a actualizar
     * @param cliente Datos actualizados
     * @return Cliente actualizado
     */
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente existente = buscarPorId(id);
        existente.setNombre(cliente.getNombre());
        existente.setNit(cliente.getNit());
        existente.setDireccion(cliente.getDireccion());
        existente.setCorreo(cliente.getCorreo());
        existente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(existente);
    }
    
    /**
     * Elimina un cliente del sistema
     * @param id ID del cliente a eliminar
     */
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
    
    /**
     * Busca clientes por nombre (coincidencia parcial)
     * @param nombre Nombre a buscar
     * @return Lista de clientes que coinciden
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
}