package com.gestorempresarial.service;

import com.gestorempresarial.model.Producto;
import com.gestorempresarial.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> listar() {
        return productoRepository.findAll();
    }
    
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public Producto registrar(Producto producto) {
        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + producto.getCodigo());
        }
        return productoRepository.save(producto);
    }
    
    public Producto actualizar(Long id, Producto producto) {
        Producto existente = buscarPorId(id);
        existente.setNombre(producto.getNombre());
        existente.setCodigo(producto.getCodigo());
        existente.setPrecio(producto.getPrecio());
        existente.setAplicaIva(producto.isAplicaIva());
        existente.setPorcentajeIva(producto.getPorcentajeIva());
        existente.setStock(producto.getStock());
        existente.setCategoria(producto.getCategoria());
        return productoRepository.save(existente);
    }
    
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
    
    public void actualizarStock(Long id, Integer stock) {
        Producto producto = buscarPorId(id);
        producto.setStock(stock);
        productoRepository.save(producto);
    }
}