/**
 * Repositorio para operaciones CRUD de productos
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.repository;

import com.gestorempresarial.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Busca un producto por su código
     * @param codigo Código del producto
     * @return Producto encontrado
     */
    Optional<Producto> findByCodigo(String codigo);
    
    /**
     * Busca productos por categoría
     * @param categoria Categoría del producto
     * @return Lista de productos
     */
    List<Producto> findByCategoria(String categoria);
    
    /**
     * Busca productos con stock bajo (menor al límite)
     * @param limiteStock Límite de stock
     * @return Lista de productos con stock bajo
     */
    List<Producto> findByStockLessThan(Integer limiteStock);
    
    /**
     * Verifica si existe un producto con el código
     * @param codigo Código a verificar
     * @return true si existe
     */
    boolean existsByCodigo(String codigo);
}