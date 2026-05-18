/**
 * Servicio para la gestión de usuarios
 * @author Andres Felipe Corzo Angarita
 */
package com.gestorempresarial.service;

import com.gestorempresarial.modelo.Usuario;
import com.gestorempresarial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return Usuario registrado
     * @throws RuntimeException si el correo ya existe
     */
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Validar que el correo no exista
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        
        usuario.setFechaRegistro(new Date());
        usuario.setActivo(true);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Autentica un usuario por correo y contraseña
     * @param correo Correo del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario autenticado o null
     */
    public Usuario autenticar(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasena, usuario.getContrasena()) && usuario.isActivo()) {
                usuario.setUltimoAcceso(new Date());
                usuarioRepository.save(usuario);
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca un usuario por ID
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
    
    /**
     * Busca un usuario por correo
     * @param correo Correo del usuario
     * @return Usuario encontrado
     */
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    
    /**
     * Actualiza los datos de un usuario
     * @param id ID del usuario
     * @param usuario Datos actualizados
     * @return Usuario actualizado
     */
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        existente.setNombre(usuario.getNombre());
        existente.setTelefono(usuario.getTelefono());
        existente.setDireccion(usuario.getDireccion());
        existente.setRol(usuario.getRol());
        return usuarioRepository.save(existente);
    }
    
    /**
     * Cambia la contraseña de un usuario
     * @param id ID del usuario
     * @param nuevaContrasena Nueva contraseña
     */
    @Transactional
    public void cambiarContrasena(Long id, String nuevaContrasena) {
        Usuario usuario = buscarPorId(id);
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
    }
    
    /**
     * Elimina (desactiva) un usuario
     * @param id ID del usuario
     */
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    
    /**
     * Obtiene usuarios activos
     * @return Lista de usuarios activos
     */
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }
    
    /**
     * Obtiene usuarios por rol
     * @param rol Rol del usuario
     * @return Lista de usuarios
     */
    public List<Usuario> listarUsuariosPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }
}