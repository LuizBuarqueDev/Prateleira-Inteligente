package com.prateleira_inteligente.controllers;


import com.prateleira_inteligente.dto.AuthDTO;
import com.prateleira_inteligente.dto.TokenDTO;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.mappers.AuthMapper;
import com.prateleira_inteligente.services.AuthService;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final AuthMapper authMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO data) {
        return authService.authenticate(data);
    }

    @PostMapping("/registrar")
    public ResponseEntity<TokenDTO> registrar(@RequestBody AuthDTO data) {
        System.out.printf(usuarioService.findByNome(data.getNomeUsuario()).toString());
        if (usuarioService.findByNome(data.getNomeUsuario()) != null) {
            throw new IllegalArgumentException("Este email já está vinculado a uma conta");
        }
        Usuario usuario = authMapper.toEntity(data);
        usuarioService.save(usuario);

        return authService.authenticate(data);
    }
}
