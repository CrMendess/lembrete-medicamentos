package com.example.lembretemedicamentos.controller;

import com.example.lembretemedicamentos.model.Usuario;
import com.example.lembretemedicamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Nome de usuário já está em uso!");
        }
        Usuario savedUser = usuarioRepository.save(usuario);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }
}
