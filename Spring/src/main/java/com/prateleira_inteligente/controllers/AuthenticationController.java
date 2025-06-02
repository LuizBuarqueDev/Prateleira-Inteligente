package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.AuthDTO;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.mappers.AuthMapper;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getNomeUsuario(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthDTO> registrar(@RequestBody AuthDTO data) {
        if (usuarioService.findByNome(data.getNomeUsuario()) != null) return ResponseEntity.badRequest().build();

        Usuario usuario = authMapper.toEntity(data);
        usuarioService.save(usuario);
        return ResponseEntity.ok().build();
    }
}
