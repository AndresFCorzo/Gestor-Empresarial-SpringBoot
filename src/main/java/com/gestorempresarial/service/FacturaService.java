/**
 * Servicio para la gestión de facturación
 * Implementa la lógica de negocio de la HU-01, HU-02 y HU-06
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.service;

import com.gestorempresarial.modelo.*;
import com.gestorempresarial.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class FacturaService {
    
    @Autowired
    private FacturaRepository facturaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    /**
     * Emite una nueva factura (HU-01)
     * @param factura Factura a emitir
     * @return Factura emitida
     * @throws RuntimeException si el cliente no existe o no hay productos
     */
    @Transactional
    public Factura emitirFactura(Factura factura) {
        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(factura.getCliente().getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        factura.setCliente(cliente);
        
        // Verificar que hay productos en la factura
        if (factura.getDetalles().isEmpty()) {
            throw new RuntimeException("La factura debe tener al menos un producto");
        }
        
        // Verificar stock y actualizar
        for (DetalleFactura detalle : factura.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getIdProducto()));
            
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            
            // Actualizar stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.calcularValores();
        }
        
        // Calcular totales y emitir
        factura.calcularTotales();
        factura.emitir();
        
        return facturaRepository.save(factura);
    }
    
    /**
     * Obtiene todas las facturas
     * @return Lista de facturas
     */
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }
    
    /**
     * Busca una factura por ID
     * @param id ID de la factura
     * @return Factura encontrada
     */
    public Factura buscarPorId(Long id) {
        return facturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }
    
    /**
     * Anula una factura existente
     * @param id ID de la factura a anular
     */
    @Transactional
    public void anularFactura(Long id) {
        Factura factura = buscarPorId(id);
        factura.anular();
        facturaRepository.save(factura);
    }
    
    /**
     * Obtiene facturas por cliente
     * @param idCliente ID del cliente
     * @return Lista de facturas
     */
    public List<Factura> listarFacturasPorCliente(Long idCliente) {
        return facturaRepository.findByClienteIdCliente(idCliente);
    }
    
    /**
     * Obtiene facturas por estado
     * @param estado Estado de la factura
     * @return Lista de facturas
     */
    public List<Factura> listarFacturasPorEstado(String estado) {
        return facturaRepository.findByEstado(estado);
    }
    
    /**
     * Genera reporte de ventas por período (HU-06)
     * @param fechaInicio Fecha inicial
     * @param fechaFin Fecha final
     * @return Lista de facturas en el período
     */
    public List<Factura> generarReporteVentas(Date fechaInicio, Date fechaFin) {
        return facturaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}