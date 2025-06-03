package com.prateleira_inteligente.services;

import com.prateleira_inteligente.config.security.TokenService;
import com.prateleira_inteligente.dto.AuthDTO;
import com.prateleira_inteligente.dto.TokenDTO;
import com.prateleira_inteligente.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public ResponseEntity<TokenDTO> authenticate(AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getNomeUsuario(), data.getSenha());
        var auth = authenticationManager.authenticate(usernamePassword);
        Usuario usuario = (Usuario) auth.getPrincipal(); // Correto
        var token = tokenService.generateToken(usuario); // Agora sim, Usuario correto
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
