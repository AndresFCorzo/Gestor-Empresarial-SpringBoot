package com.gestorempresarial.service;

import com.gestorempresarial.model.Cliente;
import com.gestorempresarial.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }
    
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    
    public Cliente registrar(Cliente cliente) {
        if (clienteRepository.existsByNit(cliente.getNit())) {
            throw new RuntimeException("Ya existe un cliente con el NIT: " + cliente.getNit());
        }
        return clienteRepository.save(cliente);
    }
    
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = buscarPorId(id);
        existente.setNombre(cliente.getNombre());
        existente.setNit(cliente.getNit());
        existente.setDireccion(cliente.getDireccion());
        existente.setEmail(cliente.getEmail());
        existente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(existente);
    }
    
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}