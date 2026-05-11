/**
 * Servicio para la gestión de productos
 * Implementa la lógica de negocio de la HU-07
 * 
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.service;

import com.gestorempresarial.modelo.Producto;
import com.gestorempresarial.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    /**
     * Registra un nuevo producto (HU-07)
     * @param producto Producto a registrar
     * @return Producto registrado
     * @throws RuntimeException si el código ya existe
     */
    @Transactional
    public Producto registrarProducto(Producto producto) {
        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + producto.getCodigo());
        }
        return productoRepository.save(producto);
    }
    
    /**
     * Obtiene todos los productos
     * @return Lista de productos
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    
    /**
     * Busca un producto por ID
     * @param id ID del producto
     * @return Producto encontrado
     */
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
    
    /**
     * Busca un producto por código
     * @param codigo Código del producto
     * @return Producto encontrado
     */
    public Producto buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + codigo));
    }
    
    /**
     * Actualiza los datos de un producto
     * @param id ID del producto
     * @param producto Datos actualizados
     * @return Producto actualizado
     */
    @Transactional
    public Producto actualizarProducto(Long id, Producto producto) {
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
    
    /**
     * Actualiza el stock de un producto
     * @param id ID del producto
     * @param nuevoStock Nuevo stock
     */
    @Transactional
    public void actualizarStock(Long id, Integer nuevoStock) {
        Producto producto = buscarPorId(id);
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }
    
    /**
     * Elimina un producto
     * @param id ID del producto
     */
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = buscarPorId(id);
        productoRepository.delete(producto);
    }
    
    /**
     * Obtiene productos con stock bajo
     * @param limite Límite de stock
     * @return Lista de productos con stock bajo
     */
    public List<Producto> productosConStockBajo(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }
}