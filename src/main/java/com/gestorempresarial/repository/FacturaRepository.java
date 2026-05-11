/**
 * Repositorio para operaciones CRUD de facturas
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.repository;

import com.gestorempresarial.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    /**
     * Busca facturas por cliente
     * @param idCliente ID del cliente
     * @return Lista de facturas del cliente
     */
    List<Factura> findByClienteIdCliente(Long idCliente);
    
    /**
     * Busca facturas por estado
     * @param estado Estado de la factura (EMITIDA, ANULADA, PENDIENTE)
     * @return Lista de facturas
     */
    List<Factura> findByEstado(String estado);
    
    /**
     * Busca facturas por rango de fechas
     * @param fechaInicio Fecha inicial
     * @param fechaFin Fecha final
     * @return Lista de facturas
     */
    List<Factura> findByFechaBetween(Date fechaInicio, Date fechaFin);
    
    /**
     * Busca facturas por número
     * @param numeroFactura Número de factura
     * @return Factura encontrada
     */
    Factura findByNumeroFactura(String numeroFactura);
}