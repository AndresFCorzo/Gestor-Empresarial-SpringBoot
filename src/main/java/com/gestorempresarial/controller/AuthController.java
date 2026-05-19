package com.gestorempresarial.controller;

import com.gestorempresarial.dto.LoginRequestDTO;
import com.gestorempresarial.model.Usuario;
import com.gestorempresarial.security.JwtUtil;
import com.gestorempresarial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint para registrar un nuevo usuario
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody LoginRequestDTO request) {
        try {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(request.getUsername());
            nuevoUsuario.setEmail(request.getUsername() + "@temp.com"); // Email temporal
            nuevoUsuario.setPassword(request.getPassword());
            nuevoUsuario.setRol("USER");
            
            Usuario usuario = usuarioService.registrar(nuevoUsuario);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("id", usuario.getId());
            response.put("username", usuario.getUsername());
            response.put("email", usuario.getEmail());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Endpoint para login de usuario
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        Usuario usuario = usuarioService.autenticar(request.getUsername(), request.getPassword());
        
        if (usuario == null) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        
        String token = jwtUtil.generateToken(usuario.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("id", usuario.getId());
        response.put("username", usuario.getUsername());
        response.put("email", usuario.getEmail());
        response.put("rol", usuario.getRol());
        response.put("mensaje", "Autenticación satisfactoria");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para verificar token
     * GET /api/auth/verify
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Token no proporcionado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        
        String token = authHeader.substring(7);
        
        if (jwtUtil.isTokenValid(token)) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Token válido");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Token inválido o expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}