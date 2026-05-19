package com.gestorempresarial.service;

import com.gestorempresarial.model.*;
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
    
    @Transactional
    public Factura emitir(Factura factura) {
        Cliente cliente = clienteRepository.findById(factura.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        factura.setCliente(cliente);
        
        if (factura.getDetalles().isEmpty()) {
            throw new RuntimeException("La factura debe tener al menos un producto");
        }
        
        for (DetalleFactura detalle : factura.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }
            
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.calcularValores();
        }
        
        factura.calcularTotales();
        factura.emitir();
        return facturaRepository.save(factura);
    }
    
    public List<Factura> listar() {
        return facturaRepository.findAll();
    }
    
    public Factura buscarPorId(Long id) {
        return facturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }
    
    @Transactional
    public void anular(Long id) {
        Factura factura = buscarPorId(id);
        factura.anular();
        facturaRepository.save(factura);
    }
    
    public List<Factura> reporteVentas(Date inicio, Date fin) {
        return facturaRepository.findByFechaBetween(inicio, fin);
    }
}