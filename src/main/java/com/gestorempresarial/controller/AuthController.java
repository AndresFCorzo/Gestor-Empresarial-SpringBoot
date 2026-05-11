/**
 * Controlador para autenticación de usuarios
 * Endpoints: /api/auth
 * 
 * @author Thomas Felipe Colmenares Perdomo
 */
package com.gestorempresarial.controller;

import com.gestorempresarial.modelo.Usuario;
import com.gestorempresarial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Registra un nuevo usuario
     * POST /api/auth/registro
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("usuario", Map.of(
                "id", nuevoUsuario.getIdUsuario(),
                "nombre", nuevoUsuario.getNombre(),
                "correo", nuevoUsuario.getCorreo(),
                "rol", nuevoUsuario.getRol()
            ));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Inicia sesión
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String contrasena = credenciales.get("contrasena");
        
        Usuario usuario = usuarioService.autenticar(correo, contrasena);
        
        if (usuario != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Login exitoso");
            response.put("usuario", Map.of(
                "id", usuario.getIdUsuario(),
                "nombre", usuario.getNombre(),
                "correo", usuario.getCorreo(),
                "rol", usuario.getRol()
            ));
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales incorrectas");
            return ResponseEntity.status(401).body(error);
        }
    }
}