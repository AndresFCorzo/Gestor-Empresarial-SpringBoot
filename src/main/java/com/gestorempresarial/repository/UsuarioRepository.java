/**
 * Repositorio para operaciones CRUD de usuarios
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.repository;

import com.gestorempresarial.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su correo electrónico
     * @param correo Correo del usuario
     * @return Usuario encontrado
     */
    Optional<Usuario> findByCorreo(String correo);
    
    /**
     * Autentica un usuario por correo y contraseña
     * @param correo Correo del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario encontrado
     */
    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);
    
    /**
     * Busca usuarios por rol
     * @param rol Rol del usuario
     * @return Lista de usuarios
     */
    List<Usuario> findByRol(String rol);
    
    /**
     * Busca usuarios activos
     * @return Lista de usuarios activos
     */
    List<Usuario> findByActivoTrue();
}