/**
 * Repositorio para operaciones CRUD de clientes
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.repository;

import com.gestorempresarial.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca un cliente por su NIT
     * @param nit NIT del cliente
     * @return Cliente encontrado o null
     */
    Optional<Cliente> findByNit(String nit);
    
    /**
     * Busca clientes por nombre (contiene)
     * @param nombre Nombre del cliente
     * @return Lista de clientes que coinciden
     */
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Verifica si existe un cliente con el NIT
     * @param nit NIT a verificar
     * @return true si existe
     */
    boolean existsByNit(String nit);
}