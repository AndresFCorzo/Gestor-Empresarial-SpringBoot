package com.gestorempresarial.repository;

import com.gestorempresarial.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByClienteId(Long clienteId);
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    List<Factura> findByFechaBetween(Date inicio, Date fin);
}